<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.smartcityhub.Business, com.smartcityhub.Service, java.util.List" %>
<%
    Business business = (Business) request.getAttribute("business");
    List<Service> services = (List<Service>) request.getAttribute("services");
    
    // Null check to prevent NullPointerException
    if (business == null) {
        response.sendRedirect("error.jsp?message=Business information not found");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Business Dashboard</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      line-height: 1.6;
      margin: 0;
      padding: 20px;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 1000px;
      margin: 0 auto;
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h2, h3 {
      color: #2c3e50;
      margin-top: 0;
    }
    .info {
      background: #f8f9fa;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 20px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
    }
    th, td {
      padding: 12px 15px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    th {
      background-color: #2c3e50;
      color: white;
    }
    tr:hover {
      background-color: #f5f5f5;
    }
    .approved {
      color: #27ae60;
      font-weight: bold;
    }
    .pending {
      color: #e67e22;
      font-weight: bold;
    }
    .status-badge {
      padding: 5px 10px;
      border-radius: 20px;
      font-size: 0.8em;
    }
    .approved-bg {
      background-color: #d5f5e3;
    }
    .pending-bg {
      background-color: #fdebd0;
    }
  </style>
</head>
<body>

  <div class="container">
    <h2>Welcome, <%= business.getBusinessName() %></h2>
    
    <div class="info">
      <p><strong>Email:</strong> <%= business.getEmail() != null ? business.getEmail() : "Not provided" %></p>
      <p><strong>Phone:</strong> <%= business.getPhone() != null ? business.getPhone() : "Not provided" %></p>
      <p><strong>Address:</strong> <%= business.getAddress() != null ? business.getAddress() : "Not provided" %></p>
      <p><strong>Status:</strong> 
        <span class="status-badge <%= business.isApproved() ? "approved approved-bg" : "pending pending-bg" %>">
          <%= business.isApproved() ? "Approved" : "Pending Approval" %>
        </span>
      </p>
    </div>

    <h3>Your Services</h3>
    <% if (services != null && !services.isEmpty()) { %>
      <table>
        <thead>
          <tr>
            <th>Service Name</th>
            <th>Description</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <% for (Service service : services) { %>
            <tr>
              <td><%= service.getServiceName() != null ? service.getServiceName() : "-" %></td>
              <td><%= service.getDescription() != null ? service.getDescription() : "-" %></td>
              <td>
                <span class="status-badge <%= service.isApproved() ? "approved approved-bg" : "pending pending-bg" %>">
                  <%= service.isApproved() ? "Approved" : "Pending" %>
                </span>
              </td>
            </tr>
          <% } %>
        </tbody>
      </table>
    <% } else { %>
      <p>No services registered yet.</p>
    <% } %>
  </div>

</body>
</html>