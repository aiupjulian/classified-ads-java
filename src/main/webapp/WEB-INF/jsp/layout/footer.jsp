<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</div>
</div>
<div class="footer">
  <div class="footer-sitemap">
    <h3>Mapa del sitio:</h3>
    <a href="index.php">Inicio</a>
    <a href="list.php">Listado</a>
    <% if (session != null && session.getAttribute("username") != null) { %>
      <a href="profile.php">Perfil</a>
      <a href="sell.php">Vender</a>
    <% } else { %>
      <a href="register.php">Registro</a>
      <a href="login.php">Login</a>
    <% } %>
  </div>
</div>
</body>
</html>