<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="./jsp/layout/header.jsp" />
<h2 class="form-title">Login</h2>
<form action="" method="post" class="form">
  <label for="username">Usuario:<span class="required"> (*)</span></label>
  <input type="text" name="username" maxlength="15" required />
  <label for="password">Contraseña:<span class="required"> (*)</span></label>
  <input type="password" name="password" maxlength="30" required />
  <button class="button">Ingresar</button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./jsp/layout/footer.jsp" />