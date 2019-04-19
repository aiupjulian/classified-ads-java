<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<Subcategory> subcategories = (ArrayList<Subcategory>)request.getAttribute("subcategories"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Subcategorías</h2>
<div class="form form-config">
  <a class="button create" href="/subcategory.jsp">Crear</a>
  <table>
    <tr>
      <th>Id</th>
      <th>Subcategoría</th>
      <th>Categoría</th>
      <th>Editar</th>
      <th>Eliminar</th>
    </tr>
    <% for (Subcategory subcategory : subcategories) { %>
      <tr class="config-list">
        <td><%= subcategory.getId() %></td>
        <td><%= subcategory.getName() %></td>
        <td><%= subcategory.getCategory().getName() %></td>
        <td class="action"><a class="button" href="/subcategory.jsp?id=<%= subcategory.getId() %>">Editar</a></td>
        <td class="action"><a class="button" href="/deleteSubcategory.jsp?id=<%= subcategory.getId() %>">Eliminar</a></td>
      </tr>
    <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />