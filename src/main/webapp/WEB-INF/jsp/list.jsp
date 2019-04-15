<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.*" %>
<%@page import="java.net.URI" %>
<%@page import="org.apache.http.client.utils.URIBuilder" %>
<%
User user = (User)session.getAttribute("user");
ArrayList<Ad> ads = (ArrayList<Ad>)request.getAttribute("ads");
Integer pages = (Integer)request.getAttribute("pages");
ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories");
ArrayList<State> states = (ArrayList<State>)request.getAttribute("states");
String name = (request.getParameter("name") != null) ? request.getParameter("name") : "";
String price1 = (request.getParameter("price1") != null) ? request.getParameter("price1") : "";
String price2 = (request.getParameter("price2") != null) ? request.getParameter("price2") : "";
Integer subcategoryId = (request.getParameter("subcategory") != null && request.getParameter("subcategory") != "") ? Integer.parseInt(request.getParameter("subcategory")) : null;
Integer cityId = (request.getParameter("city") != null && request.getParameter("city") != "") ? Integer.parseInt(request.getParameter("city")) : null;
%>
<jsp:include page="./layout/header.jsp" />
<style><%@ include file="../../css/list.css" %></style>
<h2 class="form-title">Listado</h2>
<div class="list-container">
  <form action="" method="get" class="filters" autocomplete="off">
    <label for="name">Nombre del aviso:</label>
    <input type="text" name="name" maxlength="15" value="<%= name %>">
    <fieldset>
      <legend>Precio:</legend>
      <input type="number" name="price1" maxlength="11" placeholder="Desde" value="<%= price1 %>">
      <input type="number" name="price2" maxlength="11" placeholder="Hasta" value="<%= price2 %>">
    </fieldset>
    <label for="city">Ciudad:</label>
    <select name="city">
      <% if (cityId == null) { %>
      <option selected value>Todas las ciudades</option>
      <% } else { %>
      <option value="">Todas las ciudades</option>
      <% } for (State state : states) { %>
        <optgroup label="<%= state.getName() %>">
        <% for (City city : state.getCities()) { %>
          <% if (city.getId() == cityId) { %><option value="<%= city.getId() %>" selected="selected">
          <% } else { %><option value="<%= city.getId() %>"><% } %>
            <%= city.getName() %>
          </option>
        <% } %>
        </optgroup>
      <% } %>
    </select>
    <label for="subcategory">Subcategoría:</label>
    <select name="subcategory">
      <% if (subcategoryId == null) { %>
      <option selected value>Todas las subcategorías</option>
      <% } else { %>
      <option value="">Todas las subcategorías</option>
      <% } for (Category category : categories) { %>
        <optgroup label="<%= category.getName() %>">
        <%
          for (Subcategory subcategory : category.getSubcategories()) {
            if (subcategory.getId() == subcategoryId) {
        %>
              <option value="<%= subcategory.getId() %>" selected="selected">
        <%  } else { %>
              <option value="<%= subcategory.getId() %>">
        <%  } %>
                <%= subcategory.getName() %>
              </option>
        <% } %>
        </optgroup>
      <% } %>
    </select>
    <button class="button">Buscar</button>
  </form>
  <ul class="ads-container">
    <div class="ads-list">
    <% if (ads.size() == 0) { %>
    <div style="text-align: center;">No hay avisos para mostrar con esos filtros.</div>
    <% } for (Ad ad : ads) { %>
      <li>
        <a href="/ad.jsp?id=<%= ad.getId() %>">
          <div class="image-container"><img alt="Image for Ad <%= ad.getName()%>" src="<%= ad.getImage() %>" /></div>
          <div class="ad-container">
            <div class="ad-name"><%= ad.getName() %></div>
            <div class="ad-user-name">Creado por: <%= ad.getUser().getName() %></div>
            <div class="ad-price">$<%= ad.getPrice() %></div>
            <div class="ad-date"><%= ad.getDate() %></div>
          </div>
        </a>
        <% if (user != null && user.getAdmin()) { %>
          <div class="ad-delete-admin"><a href="/deleteAd.jsp?id=<%= ad.getId() %>">Eliminar este aviso</a></div>
        <% } %>
      </li>
    <% } %>
    </div>
    <div class="ads-pagination">
      <%
      if (pages > 1) {
        for (Integer i = 1; i <= pages; i++) {
          Integer pageNumber = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
          URI uri = new URIBuilder(request.getAttribute("javax.servlet.forward.request_uri")+"?"+request.getAttribute("javax.servlet.forward.query_string")).setParameter("page", i.toString()).build();
          if (pageNumber == i) {
      %>
            <span><%= pageNumber %></span>
      <% } else { %>
            <a href="<%= uri.toString() %>"><%= i %></a>
      <% }
        }
      }
      %>
    </div>
  </ul>
</div>
<jsp:include page="./layout/footer.jsp" />