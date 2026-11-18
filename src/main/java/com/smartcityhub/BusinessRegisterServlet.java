package com.smartcityhub;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/registerBusinessOwner")
public class BusinessRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get all form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String businessName = request.getParameter("businessName");
        String businessEmail = request.getParameter("businessEmail");
        String businessPhone = request.getParameter("businessPhone");
        String businessAddress = request.getParameter("businessAddress");

        Connection conn = null;
        PreparedStatement ownerStmt = null;
        PreparedStatement businessStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Check if email already exists in Business_owners
            if (emailExists(conn, email)) {
                request.setAttribute("error", "Email already registered");
                request.getRequestDispatcher("registerBusiness.jsp").forward(request, response);
                return;
            }

            // 2. Insert owner into Business_owners
            String ownerSql = "INSERT INTO Business_owners (name, email, password, phone) VALUES (?, ?, ?, ?)";
            ownerStmt = conn.prepareStatement(ownerSql, Statement.RETURN_GENERATED_KEYS);
            ownerStmt.setString(1, name);
            ownerStmt.setString(2, email);
            ownerStmt.setString(3, password);
            ownerStmt.setString(4, phone);
            ownerStmt.executeUpdate();

            // Get generated owner ID
            rs = ownerStmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLException("Failed to get owner ID");
            }
            int ownerId = rs.getInt(1);

            // 3. Insert business (default approved=0)
            String businessSql = "INSERT INTO businesses (owner_id, business_name, email, phone, address, approved) VALUES (?, ?, ?, ?, ?, 0)";
            businessStmt = conn.prepareStatement(businessSql, Statement.RETURN_GENERATED_KEYS);
            businessStmt.setInt(1, ownerId);
            businessStmt.setString(2, businessName);
            businessStmt.setString(3, businessEmail);
            businessStmt.setString(4, businessPhone);
            businessStmt.setString(5, businessAddress);
            businessStmt.executeUpdate();

            // Get generated business ID
            ResultSet businessRs = businessStmt.getGeneratedKeys();
            int businessId = -1;
            if (businessRs.next()) {
                businessId = businessRs.getInt(1);
            }

            conn.commit(); // Commit transaction

            // Create business object for the dashboard
            Business business = new Business();
            business.setBusinessName(businessName);
            business.setEmail(businessEmail);
            business.setPhone(businessPhone);
            business.setAddress(businessAddress);
            business.setApproved(false);

            // Set session attributes
            HttpSession session = request.getSession();
            session.setAttribute("user_id", ownerId);
            session.setAttribute("owner_id", ownerId);
            session.setAttribute("business_id", businessId);
            session.setAttribute("role", "business");
            session.setAttribute("business_name", businessName);
            session.setAttribute("business_email", businessEmail);
            session.setAttribute("business_phone", businessPhone);
            session.setAttribute("business_address", businessAddress);

            // Forward to dashboard with business object
            request.setAttribute("business", business);
            request.getRequestDispatcher("businessDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("registerBusiness.jsp").forward(request, response);
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (businessStmt != null) businessStmt.close(); } catch (Exception e) {}
            try { if (ownerStmt != null) ownerStmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    private boolean emailExists(Connection conn, String email) throws SQLException {
        String sql = "SELECT 1 FROM Business_owners WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}