<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.smartcityhub.SearchServiceServlet.Business" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Search Results</title>
  <link rel="stylesheet" href="search.css">
</head>
<body>
  <div class="container">
    <h1>Search Results</h1>

    <div class="query-summary">
      You searched for: <strong><%= request.getAttribute("query") %></strong>
    </div>

    <div class="results">
      <%
        List<Business> results = (List<Business>) request.getAttribute("searchResults");
        if (results != null && !results.isEmpty()) {
          for (Business b : results) {
      %>
        <div class="result-card">
          <h3><%= b.getName() %></h3>
          <p><strong>Email:</strong> <%= b.getEmail() %></p>
          <p><strong>Phone:</strong> <%= b.getPhone() %></p>
          <p><strong>Address:</strong> <%= b.getAddress() %></p>
        </div>
      <%
          }
        } else {
      %>
        <div class="no-results">No services found for your query.</div>
      <%
        }
      %>
    </div>
  </div>
</body>
</html>
