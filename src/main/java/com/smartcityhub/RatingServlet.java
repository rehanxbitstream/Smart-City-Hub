package com.smartcityhub;

import jakarta.servlet.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement actionStmt = null;
        ResultSet rs = null;

        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            int ratingValue = Integer.parseInt(request.getParameter("rating"));
            String review = request.getParameter("review");

            conn = DBConnection.getConnection();

            // Check if rating exists
            String checkSql = "SELECT rating_id FROM ratings WHERE user_id = ? AND service_id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, serviceId);
            rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Update existing rating
                String updateSql = "UPDATE ratings SET rating_value = ?, review = ? WHERE rating_id = ?";
                actionStmt = conn.prepareStatement(updateSql);
                actionStmt.setInt(1, ratingValue);
                actionStmt.setString(2, review);
                actionStmt.setInt(3, rs.getInt("rating_id"));
            } else {
                // Insert new rating
                String insertSql = "INSERT INTO ratings (user_id, service_id, rating_value, review) VALUES (?, ?, ?, ?)";
                actionStmt = conn.prepareStatement(insertSql);
                actionStmt.setInt(1, userId);
                actionStmt.setInt(2, serviceId);
                actionStmt.setInt(3, ratingValue);
                actionStmt.setString(4, review);
            }

            int rowsAffected = actionStmt.executeUpdate();
            if (rowsAffected > 0) {
                session.setAttribute("message", "Rating submitted successfully!");
            } else {
                session.setAttribute("error", "Failed to submit rating.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error: " + e.getMessage());
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (checkStmt != null) checkStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (actionStmt != null) actionStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        response.sendRedirect("UserDashboardServlet");
    }
}