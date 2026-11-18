<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smartcityhub.Business" %>
<%@ page import="com.smartcityhub.Rating" %>
<%@ page import="com.smartcityhub.Service" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
// Debug output
List<Business> businesses = (List<Business>) request.getAttribute("businesses");
List<Rating> userRatings = (List<Rating>) request.getAttribute("userRatings");
String error = (String) request.getAttribute("error");

System.out.println("JSP DEBUG: Businesses is " + (businesses != null ? "not null" : "null"));
if (businesses != null) {
System.out.println("JSP DEBUG: Received " + businesses.size() + " businesses");
}

SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<!DOCTYPE html>

<html>
<head>
    <title>User Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .business-card { border: 1px solid #ddd; padding: 15px; margin-bottom: 20px; }
        .service-item { margin: 10px 0; padding: 10px; background: #f9f9f9; }
        .rating-stars { direction: rtl; display: inline-block; }
        .rating-stars input[type="radio"] { display: none; }
        .rating-stars label { color: #ddd; font-size: 24px; padding: 0 3px; cursor: pointer; }
        .rating-stars input[type="radio"]:checked ~ label,
        .rating-stars label:hover,
        .rating-stars label:hover ~ label { color: #f2b600; }
        .user-rating { background: #e8f4f8; padding: 10px; margin: 10px 0; }
        .error { color: red; padding: 10px; margin: 10px 0; border: 1px solid red; }
        .debug { background: #f0f0f0; padding: 10px; margin: 10px 0; }
    </style>
</head>
<body>
    <h1>User Dashboard</h1>


<%-- Error display --%>
<% if (error != null) { %>
    <div class="error">Error: <%= error %></div>
<% } %>

<%-- Debug info --%>
<div class="debug">
    <strong>Debug Info:</strong><br>
    Businesses: <%= businesses != null ? businesses.size() : 0 %><br>
    Ratings: <%= userRatings != null ? userRatings.size() : 0 %>
</div>

<h2>Your Ratings</h2>
<% if (userRatings != null && !userRatings.isEmpty()) { %>
    <% for (Rating rating : userRatings) { %>
        <div class="user-rating">
            <h3><%= rating.getServiceName() %></h3>
            <div>
                <% for (int i = 1; i <= 5; i++) { %>
                    <span style="color: <%= i <= rating.getRatingValue() ? "#f2b600" : "#ddd" %>">★</span>
                <% } %>
            </div>
            <p><%= rating.getReview() != null ? rating.getReview() : "No review provided" %></p>
            <% if (rating.getCreatedAt() != null) { %>
                <small>Rated on: <%= dateFormat.format(rating.getCreatedAt()) %></small>
            <% } %>
        </div>
    <% } %>
<% } else { %>
    <p>You haven't rated any services yet.</p>
<% } %>

<h2>Rate Services</h2>
<% if (businesses != null && !businesses.isEmpty()) { 
    for (Business business : businesses) { %>
        <div class="business-card">
            <h3><%= business.getBusinessName() %></h3>
            
            <% if (business.getServices() != null && !business.getServices().isEmpty()) { 
                for (Service service : business.getServices()) { %>
                    <div class="service-item">
                        <h4><%= service.getServiceName() %></h4>
                        <form action="RatingServlet" method="post">
                            <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">
                            
                            <div class="rating-stars">
                                <% for (int i = 5; i >= 1; i--) { %>
                                    <input type="radio" id="star<%= i %>-<%= service.getServiceId() %>" 
                                           name="rating" value="<%= i %>">
                                    <label for="star<%= i %>-<%= service.getServiceId() %>">★</label>
                                <% } %>
                            </div>
                            
                            <textarea name="review" placeholder="Your review (optional)" 
                                      rows="3" cols="50"></textarea>
                            <br>
                            <button type="submit">Submit Rating</button>
                        </form>
                    </div>
                <% } 
            } else { %>
                <p>No services available for this business.</p>
            <% } %>
        </div>
    <% } 
} else { %>
    <p>No businesses available for rating.</p>
<% } %>


</body>
</html>