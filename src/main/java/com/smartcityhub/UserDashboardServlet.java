package com.smartcityhub;

import jakarta.servlet.*;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserDashboardServlet")
public class UserDashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

    System.out.println("DEBUG: Entering UserDashboardServlet");
    
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user_id") == null) {
        System.out.println("DEBUG: No valid session - redirecting to login");
        response.sendRedirect("login.jsp");
        return;
    }

    try {
        int userId = (Integer) session.getAttribute("user_id");
        System.out.println("DEBUG: User ID in session: " + userId);
        
        // 1. Load businesses with services
        List<Business> businesses = getApprovedBusinessesWithServices();
        System.out.println("DEBUG: Loaded " + businesses.size() + " businesses");
        
        // 2. Load user ratings
        List<Rating> userRatings = getUserRatings(userId);
        System.out.println("DEBUG: Loaded " + userRatings.size() + " ratings");
        
        // Set attributes
        request.setAttribute("businesses", businesses);
        request.setAttribute("userRatings", userRatings);
        
    } catch (SQLException e) {
        System.out.println("ERROR: Database exception: " + e.getMessage());
        e.printStackTrace();
        request.setAttribute("error", "Database error: " + e.getMessage());
    }
    
    request.getRequestDispatcher("userDashboard.jsp").forward(request, response);

}

private List<Business> getApprovedBusinessesWithServices() throws SQLException {
    List<Business> businesses = new ArrayList<>();
    
    String sql = "SELECT b.business_id, b.business_name, " +
               "s.service_id, s.service_name " +
               "FROM businesses b " +
               "LEFT JOIN services s ON b.business_id = s.business_id AND s.approved = 1 " +
               "WHERE b.approved = 1 " +
               "ORDER BY b.business_id, s.service_id";
    
    System.out.println("DEBUG: Executing query: " + sql);
    
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        System.out.println("DEBUG: Query executed successfully");
        
        Business currentBusiness = null;
        while (rs.next()) {
            int businessId = rs.getInt("business_id");
            String businessName = rs.getString("business_name");
            
            if (currentBusiness == null || currentBusiness.getBusinessId() != businessId) {
                currentBusiness = new Business();
                currentBusiness.setBusinessId(businessId);
                currentBusiness.setBusinessName(businessName);
                currentBusiness.setServices(new ArrayList<>());
                businesses.add(currentBusiness);
                System.out.println("DEBUG: Added business: " + businessName + " (ID: " + businessId + ")");
            }
            
            if (rs.getObject("service_id") != null) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                currentBusiness.getServices().add(service);
                System.out.println("DEBUG: Added service: " + service.getServiceName() + " to business ID: " + businessId);
            }
        }
    } catch (SQLException e) {
        System.out.println("ERROR: Database query failed: " + e.getMessage());
        throw e;
    }
    
    return businesses;
}

private List<Rating> getUserRatings(int userId) throws SQLException {
    List<Rating> ratings = new ArrayList<>();
    
    String sql = "SELECT r.*, s.service_name FROM ratings r " +
                "JOIN services s ON r.service_id = s.service_id " +
                "WHERE r.user_id = ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setRatingId(rs.getInt("rating_id"));
                rating.setServiceId(rs.getInt("service_id"));
                rating.setRatingValue(rs.getInt("rating_value"));
                rating.setReview(rs.getString("review"));
                rating.setServiceName(rs.getString("service_name"));
                ratings.add(rating);
            }
        }
    }
    return ratings;
}
}