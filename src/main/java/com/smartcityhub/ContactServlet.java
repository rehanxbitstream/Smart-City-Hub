package com.smartcityhub;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        try {
            // Get connection from your existing DBConnection class
            conn = DBConnection.getConnection();
            
            // Prepare SQL statement
            String sql = "INSERT INTO contact_messages (name, email, message, submission_date) " +
                         "VALUES (?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, message);
            
            // Execute the statement
            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Set appropriate message based on operation success
        if (success) {
            request.setAttribute("successMessage", 
                "Thank you for contacting us! Your message has been sent successfully.");
        } else {
            request.setAttribute("errorMessage", 
                "There was an error sending your message. Please try again later.");
        }
        
        // Forward back to the contact page with the message
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }
}