<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Login - Smart City Hub</title>
  <link rel="stylesheet" href="loginjsp.css"/>
</head>
<body>

  <div class="login-container">
    <h2>Login to Smart City Hub</h2>

    <form action="LoginServlet" method="post">
      <label for="email">Email</label>
      <input type="email" name="email" id="email" required />

      <label for="password">Password</label>
      <input type="password" name="password" id="password" required />

      <label for="role">Login as</label>
      <select name="role" id="role" required>
        <option value="user">User</option>
        <option value="business">Business Owner</option>
        <option value="admin">Admin</option>
      </select>

      <button type="submit">Login</button>
    </form>

    <div class="register-links">
      <p>Don't have an account?</p>
      <a href="registerUser.jsp" class="btn-link">Register as User</a>
      <a href="registerBusiness.jsp" class="btn-link">Register as Business Owner</a>
    </div>
  </div>

</body>
</html>
