<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
String buttonText = "Crear";
ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories");
Subcategory subcategory = null;
if (request.getAttribute("subcategory") != null) {
  subcategory = (Subcategory)request.getAttribute("subcategory");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title"><%= buttonText %> Subcategoría</h2>
<form action="" method="post" class="form">
  <label for="category">Categoría:<span class="required"> (*)</span></label>
  <select name="category" required>
    <% for (Category category : categories) { %>
      <% if (subcategory != null && subcategory.getCategory().getId() == category.getId()) { %><option value="<%= category.getId() %>" selected="selected">
      <% } else { %><option value="<%= category.getId() %>"><% } %>
        <%= category.getName() %>
      </option>
    <% } %>
  </select>
  <label for="name">Nombre de la subcategoría:<span class="required"> (*)</span></label>
  <% if (subcategory == null) { %><input type="text" name="name" maxlength="20" required>
  <% } else { %><input type="text" name="name" maxlength="20" required value="<%= subcategory.getName() %>"><% } %>
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />