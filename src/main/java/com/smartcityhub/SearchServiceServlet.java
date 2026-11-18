package com.smartcityhub;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/SearchServiceServlet")
public class SearchServiceServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Business {
        private String name, email, phone, address;

        public Business(String name, String email, String phone, String address) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getAddress() { return address; }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Business> results = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT business_name, email, phone, address FROM businesses WHERE business_name LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                results.add(new Business(
                    rs.getString("business_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                ));
            }

            request.setAttribute("searchResults", results);
            request.setAttribute("query", query);
            RequestDispatcher rd = request.getRequestDispatcher("searchResults.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
