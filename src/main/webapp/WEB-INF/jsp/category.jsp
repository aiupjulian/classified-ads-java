<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
String buttonText = "Crear";
Category category = null;
if (request.getAttribute("category") != null) {
  category = (Category)request.getAttribute("category");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title"><%= buttonText %> Categoría</h2>
<form action="" method="post" class="form">
  <label for="name">Nombre de la categoría:<span class="required"> (*)</span></label>
  <% if (category == null) { %><input type="text" name="name" maxlength="20" required>
  <% } else { %><input type="text" name="name" maxlength="20" required value="<%= category.getName() %>"><% } %>
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />