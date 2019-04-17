<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
String buttonText = "Crear";
State state = null;
if (request.getAttribute("state") != null) {
  state = (State)request.getAttribute("state");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title"><%= buttonText %> Provincia</h2>
<form action="" method="post" class="form">
  <label for="name">Nombre de la provincia:<span class="required"> (*)</span></label>
  <% if (state == null) { %><input type="text" name="name" maxlength="20" required>
  <% } else { %><input type="text" name="name" maxlength="20" required value="<%= state.getName() %>"><% } %>
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />