<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="entity.User" %>
</div>
</div>
<div class="footer">
  <div class="footer-sitemap">
    <h3>Mapa del sitio:</h3>
    <a href="index.jsp">Inicio</a>
    <a href="list.jsp">Listado</a>
    <%
      User user = (User)session.getAttribute("user");
      if (user != null) {
    %>
      <a href="profile.jsp">Perfil</a>
      <a href="sell.jsp">Vender</a>
    <% } else { %>
      <a href="register.jsp">Registro</a>
      <a href="login.jsp">Login</a>
    <% } %>
  </div>
</div>
</body>
</html>