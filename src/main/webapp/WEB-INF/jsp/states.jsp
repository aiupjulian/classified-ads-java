<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<State> states = (ArrayList<State>)request.getAttribute("states"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Provincias</h2>
<div class="form form-config">
  <a class="button create" href="/state.jsp">Crear</a>
  <table>
    <tr>
      <th>Id</th>
      <th>Provincia</th>
      <th>Editar</th>
      <th>Eliminar</th>
    </tr>
    <% for (State state : states) { %>
      <tr class="config-list">
        <td><%= state.getId() %></td>
        <td><%= state.getName() %></td>
        <td class="action"><a class="button" href="/state.jsp?id=<%= state.getId() %>">Editar</a></td>
        <td class="action"><a class="button" href="/deleteState.jsp?id=<%= state.getId() %>">Eliminar</a></td>
      </tr>
    <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />