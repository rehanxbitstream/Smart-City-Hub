
package com.smartcityhub;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/registerUser")
public class UserRegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, 'user')";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password); // Hash in production
                ps.setString(4, phone);

                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("login.jsp?success=true");
                } else {
                    request.setAttribute("error", "Registration failed.");
                    request.getRequestDispatcher("registerUser.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error.");
            request.getRequestDispatcher("registerUser.jsp").forward(request, response);
        }
    }
}
