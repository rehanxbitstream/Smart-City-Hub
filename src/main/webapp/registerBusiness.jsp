<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Register Business - Smart City Hub</title>
  <link rel="stylesheet" href="RegisterBusiness.css"/>
</head>
<body>

  <div class="register-container">
    <h2>Business Owner Registration</h2>

    <form action="registerBusinessOwner" method="post">
      <h3>Owner Info</h3>
      <input type="text" name="name" placeholder="Owner Name" required />
      <input type="email" name="email" placeholder="Owner Email" required />
      <input type="password" name="password" placeholder="Password" required />
      <input type="text" name="phone" placeholder="Phone" required />

      <h3>Business Info</h3>
      <input type="text" name="businessName" placeholder="Business Name" required />
      <input type="email" name="businessEmail" placeholder="Business Email" required />
      <input type="text" name="businessPhone" placeholder="Business Phone" required />
      <textarea name="businessAddress" placeholder="Business Address" rows="3" required></textarea>

      <button type="submit">Register Business</button>
    </form>

    <div class="footer">
      Already have an account? <a href="login.jsp">Login here</a>
    </div>
  </div>

</body>
</html>
