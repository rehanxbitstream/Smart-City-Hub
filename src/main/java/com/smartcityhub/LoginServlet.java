package com.smartcityhub;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String selectedRole = request.getParameter("role");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            HttpSession session = request.getSession();

            // 1. Admin Login Check
            if ("admin".equalsIgnoreCase(selectedRole)) {
                if ("admin@gmail.com".equals(email) && "admin123".equals(password)) {
                    session.setAttribute("user_id", "admin");
                    session.setAttribute("name", "Admin");
                    session.setAttribute("email", email);
                    session.setAttribute("role", "admin");
                    response.sendRedirect("adminDashboard.jsp");
                    return;
                } else {
                    response.sendRedirect("login.jsp?error=invalid_admin_credentials");
                    return;
                }
            }

            conn = DBConnection.getConnection();
            if (conn == null) {
                response.sendRedirect("login.jsp?error=database_error");
                return;
            }

            // 2. Business Owner Login
            if ("business".equalsIgnoreCase(selectedRole)) {
                String businessQuery = 
                    "SELECT bo.owner_id, bo.name, b.business_id, b.business_name " +
                    "FROM Business_owners bo " +
                    "JOIN businesses b ON bo.owner_id = b.owner_id " +
                    "WHERE bo.email = ? AND bo.password = ?";

                ps = conn.prepareStatement(businessQuery);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs != null && rs.next()) {
                    session.setAttribute("user_id", rs.getInt("owner_id"));
                    session.setAttribute("owner_id", rs.getInt("owner_id"));
                    session.setAttribute("business_id", rs.getInt("business_id"));
                    session.setAttribute("business_name", rs.getString("business_name"));
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("email", email);
                    session.setAttribute("role", "business");
                    
                    response.sendRedirect("businessDashboardServlet");
                } else {
                    response.sendRedirect("login.jsp?error=invalid_business_credentials");
                }
                return;
            }

            // 3. Regular User Login
            if ("user".equalsIgnoreCase(selectedRole)) {
                String userQuery = "SELECT user_id, name FROM users WHERE email = ? AND password = ?";
                ps = conn.prepareStatement(userQuery);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs != null && rs.next()) {
                    session.setAttribute("user_id", rs.getInt("user_id"));
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("email", email);
                    session.setAttribute("role", "user");
                    
                    response.sendRedirect("userDashboard.jsp");
                } else {
                    response.sendRedirect("login.jsp?error=invalid_user_credentials");
                }
                return;
            }

            // 4. Invalid Role Selected
            response.sendRedirect("login.jsp?error=invalid_role_selected");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=database_error");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unexpected_error");
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}