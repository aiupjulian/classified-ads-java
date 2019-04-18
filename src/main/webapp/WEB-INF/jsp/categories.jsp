<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Categorías</h2>
<div class="form">
  <a class="button" href="/category.jsp">Crear</a>
  <% for (Category category : categories) { %>
    <div class="config-list">
      <div><%= category.getId() %></div>
      <div><%= category.getName() %></div>
      <div class="actions">
        <a class="button" href="/category.jsp?id=<%= category.getId() %>">Editar</a>
        <a class="button" href="/deleteState.jsp?id=<%= category.getId() %>">Eliminar</a>
      </div>
    </div>
  <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />