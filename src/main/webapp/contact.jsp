<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Contact Us - City Services</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background-color: #f0f4f8;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      margin: 0;
    }
    
    .contact-container {
      background: #fff;
      padding: 2rem 3rem;
      border-radius: 10px;
      box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
      width: 500px;
      margin: 2rem;
    }
    
    .contact-container h2 {
      text-align: center;
      color: #1e3a8a;
      margin-bottom: 0.5rem;
    }
    
    .contact-container .description {
      text-align: center;
      font-size: 14px;
      color: #555;
      margin-bottom: 1.5rem;
    }
    
    form label {
      display: block;
      margin-bottom: 0.3rem;
      color: #333;
      font-size: 14px;
      font-weight: 500;
    }
    
    form input,
    form textarea {
      width: 100%;
      padding: 0.6rem;
      margin-bottom: 1rem;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-family: inherit;
      font-size: 14px;
      resize: none;
      transition: border-color 0.3s;
    }
    
    form input:focus,
    form textarea:focus {
      outline: none;
      border-color: #1e3a8a;
    }
    
    form button {
      width: 100%;
      padding: 0.7rem;
      background-color: #1e3a8a;
      color: white;
      border: none;
      border-radius: 5px;
      font-weight: bold;
      cursor: pointer;
      font-size: 14px;
      transition: background-color 0.3s;
    }
    
    form button:hover {
      background-color: #0a3e72;
    }
    
    .footer {
      text-align: center;
      margin-top: 1.5rem;
    }
    
    .footer a {
      color: #062b50;
      text-decoration: none;
      font-size: 14px;
      transition: color 0.3s;
    }
    
    .footer a:hover {
      color: #1e3a8a;
      text-decoration: underline;
    }

    /* Message styles */
    .alert {
      padding: 0.8rem 1rem;
      margin-bottom: 1.5rem;
      border-radius: 5px;
      font-size: 14px;
      text-align: center;
    }
    
    .alert-success {
      background-color: #d1fae5;
      color: #065f46;
      border: 1px solid #a7f3d0;
    }
    
    .alert-error {
      background-color: #fee2e2;
      color: #b91c1c;
      border: 1px solid #fecaca;
    }

    /* Responsive adjustments */
    @media (max-width: 600px) {
      .contact-container {
        width: 90%;
        padding: 1.5rem;
      }
    }
  </style>
</head>
<body>

  <div class="contact-container">
    <h2>Contact Us</h2>
    <p class="description">Have a question, feedback, or just want to say hi? Fill out the form below and we'll get back to you shortly.</p>

    <%-- Success/Error Messages using scriptlets --%>
    <%
      String successMessage = (String) request.getAttribute("successMessage");
      String errorMessage = (String) request.getAttribute("errorMessage");
      
      if (successMessage != null) {
    %>
      <div class="alert alert-success">
        <%= successMessage %>
      </div>
    <%
      }
      
      if (errorMessage != null) {
    %>
      <div class="alert alert-error">
        <%= errorMessage %>
      </div>
    <%
      }
    %>

    <form action="ContactServlet" method="post">
      <label for="name">Your Name</label>
      <input type="text" name="name" id="name" required value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">

      <label for="email">Your Email</label>
      <input type="email" name="email" id="email" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">

      <label for="message">Message</label>
      <textarea name="message" id="message" rows="5" required><%= request.getParameter("message") != null ? request.getParameter("message") : "" %></textarea>

      <button type="submit">Send Message</button>
    </form>

    <div class="footer">
      <a href="index.jsp">← Back to Home</a>
    </div>
  </div>

</body>
</html>