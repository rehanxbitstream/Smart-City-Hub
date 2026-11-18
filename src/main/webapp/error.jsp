<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .error-container { 
            max-width: 600px; 
            margin: 50px auto; 
            padding: 20px; 
            border: 1px solid #ff4444; 
            background-color: #ffebee; 
            border-radius: 5px;
        }
        .error-message {
            color: #d32f2f;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h2>Error Occurred</h2>
        <div class="error-message">
            <%= request.getParameter("message") != null ? 
                request.getParameter("message") : "An unknown error occurred" %>
        </div>
        <a href="login.jsp">Return to Login</a>
    </div>
</body>
</html>