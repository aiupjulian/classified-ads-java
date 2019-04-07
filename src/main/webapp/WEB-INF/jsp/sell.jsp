<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
String buttonText = "Crear";
ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories");
ArrayList<State> states = (ArrayList<State>)request.getAttribute("states");
Ad ad = null;
if (request.getAttribute("ad") != null) {
  ad = (Ad)request.getAttribute("ad");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Crear Aviso</h2>
<form action="" method="post" class="form" enctype="multipart/form-data">
  <label for="name">Nombre del aviso:<span class="required"> (*)</span></label>
  <% if (ad == null) { %><input type="text" name="name" maxlength="15" required>
  <% } else { %><input type="text" name="name" maxlength="15" required value="<%= ad.getName() %>"><% } %>
  <label for="description">Descripción:<span class="required"> (*)</span></label>
  <% if (ad == null) { %><input type="text" name="description" maxlength="60" required>
  <% } else { %><input type="text" name="description" maxlength="60" required value="<%= ad.getDescription() %>"><% } %>
  <label for="price">Precio:<span class="required"> (*)</span></label>
  <% if (ad == null) { %><input type="number" name="price" maxlength="11" required>
  <% } else { %><input type="number" name="price" maxlength="11" required value=<%= ad.getPrice() %>><% } %>
  <label for="city">Ciudad:<span class="required"> (*)</span></label>
  <select name="city" required>
    <% for (State state : states) { %>
      <optgroup label="<%= state.getName() %>">
      <% for (City city : state.getCities()) { %>
        <% if (ad != null && city.getId() == ad.getCity().getId()) { %><option value="<%= city.getId() %>" selected="selected">
        <% } else { %><option value="<%= city.getId() %>"><% } %>
          <%= city.getName() %>
        </option>
      <% } %>
      </optgroup>
    <% } %>
  </select>
  <label for="subcategory">Subcategoría:<span class="required"> (*)</span></label>
  <select name="subcategory" required>
    <% for (Category category : categories) { %>
      <optgroup label="<%= category.getName() %>">
      <% for (Subcategory subcategory : category.getSubcategories()) { %>
        <% if (ad != null && subcategory.getId() == ad.getSubcategory().getId()) { %><option value="<%= subcategory.getId() %>" selected="selected">
        <% } else { %><option value="<%= subcategory.getId() %>"><% } %>
          <%= subcategory.getName() %>
        </option>
      <% } %>
      </optgroup>
    <% } %>
  </select>
  <label for="image">Imágen:<span class="required"> (*)</span></label>
  <input type="hidden" name="MAX_FILE_SIZE" value="2000000">
  <input type="file" name="image" accept="image/png, image/jpeg">
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />