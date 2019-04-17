<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<% ArrayList<State> states = (ArrayList<State>)request.getAttribute("states"); %>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Provincias</h2>
<div class="form">
  <a class="button" href="/state.jsp">Crear</a>
  <% for (State state : states) { %>
    <div>
      <div><%= state.getId() %></div>
      <div><%= state.getName() %></div>
      <div class="actions">
        <a class="button" href="/state.jsp?id=<%= state.getId() %>">Editar</a>
        <a class="button" href="/deleteState.jsp?id=<%= state.getId() %>">Eliminar</a>
      </div>
    </div>
  <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />