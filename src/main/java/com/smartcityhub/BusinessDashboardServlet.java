package com.smartcityhub;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/businessDashboardServlet")
public class BusinessDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user_id") != null) {
          /*  userId =  session.getAttribute("user_id");*/
            int businessId = (int) session.getAttribute("business_id");
            
            try {
                // Get business details
                Business business = getBusinessDetails(businessId);
                request.setAttribute("business", business);
                
                // Get services
                List<Service> services = getBusinessServices(businessId);
                request.setAttribute("services", services);
                
                request.getRequestDispatcher("businessDashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private Business getBusinessDetails(int businessId) throws SQLException {
        Business business = null;
        String query = "SELECT * FROM businesses WHERE business_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, businessId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    business = new Business();
                    business.setBusinessId(rs.getInt("business_id"));
                    business.setBusinessName(rs.getString("business_name"));
                    business.setEmail(rs.getString("email"));
                    business.setPhone(rs.getString("phone"));
                    business.setAddress(rs.getString("address"));
                    business.setApproved(rs.getBoolean("approved"));
                }
            }
        }
        return business;
    }

    private List<Service> getBusinessServices(int businessId) throws SQLException {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services WHERE business_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, businessId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setServiceId(rs.getInt("service_id"));
                    service.setBusinessId(rs.getInt("business_id"));
                    service.setServiceName(rs.getString("service_name"));
                    service.setDescription(rs.getString("description"));
                    
                    service.setApproved(rs.getBoolean("approved"));
                    services.add(service);
                }
            }
        }
        return services;
    }
}