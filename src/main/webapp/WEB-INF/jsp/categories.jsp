<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Categorías</h2>
<div class="form form-config">
  <a class="button create" href="/category.jsp">Crear</a>
  <table>
    <tr>
      <th>Id</th>
      <th>Categoría</th>
      <th>Editar</th>
      <th>Eliminar</th>
    </tr>
    <% for (Category category : categories) { %>
      <tr class="config-list">
        <td><%= category.getId() %></td>
        <td><%= category.getName() %></td>
        <td class="action"><a class="button" href="/category.jsp?id=<%= category.getId() %>">Editar</a></td>
        <td class="action"><a class="button" href="/deleteCategory.jsp?id=<%= category.getId() %>">Eliminar</a></td>
      </tr>
    <% } %>
  </table>
</div>
<jsp:include page="./layout/footer.jsp" />