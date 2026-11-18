package com.smartcityhub;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Business> pendingBusinesses = getPendingBusinesses();
            request.setAttribute("businesses", pendingBusinesses);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminDashboard.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading pending businesses: " + e.getMessage());
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String businessIdStr = request.getParameter("businessId");
        
        if (action == null || businessIdStr == null) {
            request.setAttribute("error", "Missing required parameters");
            doGet(request, response);
            return;
        }

        try {
            int businessId = Integer.parseInt(businessIdStr);
            
            if ("approve".equalsIgnoreCase(action)) {
                updateBusinessStatus(businessId, 1);
                request.setAttribute("message", "Business approved successfully");
            } 
            else if ("reject".equalsIgnoreCase(action)) {
                updateBusinessStatus(businessId, 0);
                request.setAttribute("message", "Business rejected");
            }
            
            doGet(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing request: " + e.getMessage());
            doGet(request, response);
        }
    }

    private List<Business> getPendingBusinesses() throws Exception {
        List<Business> businesses = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT b.business_id, b.business_name, b.email, b.phone, b.address, b.approved, " +
                 "b.owner_id " + // Only selecting owner_id since we'll fetch owner details separately
                 "FROM Businesses b " +
                 "WHERE b.approved = 0")) {
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Business biz = new Business();
                    biz.setBusinessId(rs.getInt("business_id"));
                    biz.setBusinessName(rs.getString("business_name"));
                    biz.setEmail(rs.getString("email"));
                    biz.setPhone(rs.getString("phone"));
                    biz.setAddress(rs.getString("address"));
                    biz.setApproved(rs.getBoolean("approved"));
                    biz.setOwnerId(rs.getInt("owner_id"));
                    
                    // Fetch owner details separately
                    fetchOwnerDetails(biz, conn);
                    
                    businesses.add(biz);
                }
            }
        }
        
        return businesses;
    }

    private void fetchOwnerDetails(Business business, Connection conn) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement(
                 "SELECT name, email, phone FROM Business_owners WHERE owner_id = ?")) {
            
            stmt.setInt(1, business.getOwnerId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    business.setOwnerName(rs.getString("name"));
                    business.setOwnerEmail(rs.getString("email"));
                    business.setOwnerPhone(rs.getString("phone"));
                }
            }
        }
    }

    private void updateBusinessStatus(int businessId, int status) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE Businesses SET approved = ? WHERE business_id = ?")) {
            
            stmt.setInt(1, status);
            stmt.setInt(2, businessId);
            stmt.executeUpdate();
        }
    }
}