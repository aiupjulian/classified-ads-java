<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
String buttonText = "Crear";
ArrayList<State> states = (ArrayList<State>)request.getAttribute("states");
City city = null;
if (request.getAttribute("city") != null) {
  city = (City)request.getAttribute("city");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title"><%= buttonText %> Ciudad</h2>
<form action="" method="post" class="form">
  <label for="state">Provincia:<span class="required"> (*)</span></label>
  <select name="state" required>
    <% for (State state : states) { %>
      <% if (city != null && city.getState().getId() == state.getId()) { %><option value="<%= state.getId() %>" selected="selected">
      <% } else { %><option value="<%= state.getId() %>"><% } %>
        <%= state.getName() %>
      </option>
    <% } %>
  </select>
  <label for="name">Nombre de la ciudad:<span class="required"> (*)</span></label>
  <% if (city == null) { %><input type="text" name="name" maxlength="20" required>
  <% } else { %><input type="text" name="name" maxlength="20" required value="<%= city.getName() %>"><% } %>
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />