<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>City Services</title>
  <link rel="stylesheet" href="styles.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <style>
        .icons {
            display: flex;
            justify-content: center;
            gap: 2rem;
            margin-top: 1rem;
            flex-wrap: wrap;
        }

        .icon-card {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 1rem;
            width: 100px;
            text-align: center;
        }

        .icon-card i {
            font-size: 2rem;
            color: #1e3a8a;
            margin-bottom: 0.5rem;
        }

        .icon-card:hover {
            background-color: #f0f4ff;
            transform: scale(1.05);
            transition: all 0.3s ease;
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .icon-link {
            text-decoration: none;
            color: inherit;
        }
    </style>
  
</head>
<body>
  <header class="hero">
    <div class="navbar">
      <div class="logo">Smart <span>City Hub</span></div>
      <nav>
        <ul>
          <li><a href="index.jsp">Home</a></li>
          <li><a href="about.jsp">About</a></li>
          <li><a href="services.html">Services</a></li>
          <li><a href="contact.jsp">Contact</a></li>
          <li><button class="login-btn" onclick="location.href='login.jsp'">Login</button></li>
        </ul>
      </nav>
    </div>
    <div class="hero-content">
  <h1>Find services in your city</h1>
  <form class="search-bar" action="SearchServiceServlet" method="get">
    <input type="text" name="query" placeholder="Search services..." required />
    <button type="submit">Search</button>
  </form>
</div>

  </header>

<section class="categories">
  <h2>Categories</h2>
  <div class="icons">
    <div class="icon-card">
      <a href="services.html" class="icon-link">
        <i class="fas fa-hospital"></i>
        <p>Hospitals</p>
      </a>
    </div>
    <div class="icon-card">
      <a href="services.html" class="icon-link">
        <i class="fas fa-graduation-cap"></i>
        <p>Colleges</p>
      </a>
    </div>
    <div class="icon-card">
      <a href="services.html" class="icon-link">
        <i class="fas fa-utensils"></i>
        <p>Restaurants</p>
      </a>
    </div>
    <div class="icon-card">
      <a href="services.html" class="icon-link">
        <i class="fas fa-bus"></i>
        <p>Transport</p>
      </a>
    </div>
    <div class="icon-card">
      <a href="services.html" class="icon-link">
        <i class="fas fa-shopping-bag"></i>
        <p>Shops</p>
      </a>
    </div>
  </div>
</section>


  <section class="featured">
    <h2>Featured Listings</h2>
    <div class="cards">
      <div class="card">
        <div class="img-box"><img src="images/hospital.jpg" alt="Hospital" ></div>
        <h3>Hospitals</h3>
        <p>★★★★★</p>
        <p>Top multi-specialty hospitals offering 24/7 emergency services and modern facilities.</p>
      </div>
      <div class="card">
        <div class="img-box"><img src="images/college.jpg" alt="college" ></div>
        <h3>Colleges</h3>
        <p>★★★★★</p>
        <p>Leading academic institutions with diverse programs and excellent faculty.</p>
      </div>
      <div class="card">
        <div class="img-box"><img src="images/restaurant.jpg" alt="Restaurant" ></div>
        <h3>Restaurants</h3>
        <p>★★★★★</p>
        <p>Best places to dine with delicious cuisine and friendly ambiance.</p>
      </div>
      <div class="card">
        <div class="img-box"><img src="images/bus.jpg" alt="bus" ></div>
        <h3>Transport</h3>
        <p>★★★★★</p>
        <p>Reliable city transport including buses, taxis, and app-based services.</p>
      </div>
      <div class="card">
        <div class="img-box"><img src="shop.jpg" alt="shop images" ></div>
        <h3>Shops</h3>
        <p>★★★★★</p>
        <p>Popular local markets and shopping centers for all your daily needs.</p>
      </div>
    </div>
  </section>

  <section class="testimonial">
    <blockquote>
      "All Your City Needs, One Hub"<br>
    </blockquote>
  </section>

  <footer>
    <div class="footer-col">
      <h4>Contact Info</h4>
      <p>Email: contact@smartcityhub.com<br>Phone: +91-9876543210</p>
    </div>
    <div class="footer-col">
      <h4>Quick Links</h4>
      <p><a href="contact.jsp" >Contact</a>| <a href="services.jsp" >Services</a>| 	</p>
    </div>
  </footer>
</body>
</html>