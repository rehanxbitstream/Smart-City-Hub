<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.smartcityhub.Business" %>
<%
    List<Business> businesses = (List<Business>) request.getAttribute("businesses");
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Admin Dashboard - Approve Businesses</title>
    <link rel="stylesheet" href="adminDashboard.css"/>
</head>
<body>
    <header class="admin-header">
        <h1>Smart City Hub - Admin Dashboard</h1>
        <a href="index.jsp" class="logout-btn">Logout</a>
    </header>

    <div class="admin-container">
        <nav class="admin-sidebar">
            <ul>
                <li class="active"><a href="adminDashboard">Business Approvals</a></li>
                <li><a href="userManagement">User Management</a></li>
                <li><a href="reports">Reports</a></li>
                <li><a href="settings">Settings</a></li>
            </ul>
        </nav>

        <main class="admin-content">
            <h2>Pending Business Approvals</h2>
            
            <% if (error != null) { %>
                <div class="alert error">
                    <strong>Error:</strong> <%= error %>
                </div>
            <% } %>
            
            <% if (message != null) { %>
                <div class="alert success">
                    <%= message %>
                </div>
            <% } %>

            <% if (businesses != null && !businesses.isEmpty()) { %>
            <div class="table-responsive">
                <table class="business-table">
                    <thead>
                        <tr>
                            <th>Business Name</th>
                            <th>Contact Email</th>
                            <th>Phone</th>
                            <th>Address</th>
                            <th>Owner</th>
                            <th>Owner Contact</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Business b : businesses) { %>
                        <tr>
                            <td><%= b.getBusinessName() %></td>
                            <td><%= b.getEmail() %></td>
                            <td><%= b.getPhone() %></td>
                            <td><%= b.getAddress() %></td>
                            <td>
                                <%= b.getOwnerName() %><br>
                                <small><%= b.getOwnerEmail() %></small>
                            </td>
                            <td><%= b.getOwnerPhone() %></td>
                            <td class="actions">
                                <form method="post" action="adminDashboard" class="approval-form">
                                    <input type="hidden" name="businessId" value="<%= b.getBusinessId() %>"/>
                                    <button type="submit" name="action" value="approve" class="btn approve">
                                        <i class="fas fa-check"></i> Approve
                                    </button>
                                    <button type="submit" name="action" value="reject" class="btn reject">
                                        <i class="fas fa-times"></i> Reject
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <% } else { %>
                <div class="empty-state">
                    <i class="fas fa-clipboard-check"></i>
                    <h3>No Pending Business Approvals</h3>
                    <p>There are currently no businesses waiting for approval.</p>
                </div>
            <% } %>
        </main>
    </div>

    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
</body>
</html>