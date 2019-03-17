<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Registro</h2>
<form action="" method="post" class="form">
  <label for="username">Usuario:<span class="required"> (*)</span></label>
  <input type="text" name="username" maxlength="15" required>
  <label for="password">Contraseña:<span class="required"> (*)</span></label>
  <input type="password" name="password" maxlength="30" required>
  <label for="name">Nombre:<span class="required"> (*)</span></label>
  <input type="text" name="name" maxlength="30" required>
  <label for="phone">Teléfono:<span class="required"> (*)</span></label>
  <input type="tel" name="phone" maxlength="15" required>
  <label for="email">Email:<span class="required"> (*)</span></label>
  <input type="email" name="email" maxlength="40" required>
  <button class="button">Registrarse</button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />