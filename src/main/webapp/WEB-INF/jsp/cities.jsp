<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<City> cities = (ArrayList<City>)request.getAttribute("cities"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Ciudades</h2>
<div class="form form-config">
  <a class="button create" href="/city.jsp">Crear</a>
  <table>
    <tr>
      <th>Id</th>
      <th>Ciudad</th>
      <th>Provincia</th>
      <th>Editar</th>
      <th>Eliminar</th>
    </tr>
    <% for (City city : cities) { %>
      <tr class="config-list">
        <td><%= city.getId() %></td>
        <td><%= city.getName() %></td>
        <td><%= city.getState().getName() %></td>
        <td class="action"><a class="button" href="/city.jsp?id=<%= city.getId() %>">Editar</a></td>
        <td class="action"><a class="button" href="/deleteCity.jsp?id=<%= city.getId() %>">Eliminar</a></td>
      </tr>
    <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />