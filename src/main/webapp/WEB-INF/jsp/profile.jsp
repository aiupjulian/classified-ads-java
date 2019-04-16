<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="entity.User" %>
<%@page import="entity.Ad" %>
<%
User user = (User)session.getAttribute("user");
ArrayList<Ad> ads = new ArrayList<Ad>();
ads = (ArrayList<Ad>)request.getAttribute("ads");
%>
<jsp:include page="./layout/header.jsp" />
<style><%@ include file="../../css/profile.css" %></style>
<h2 class="form-title">Datos del usuario:</h2>
<div class="form">
  <div><b>Nombre:</b> <%= user.getName() %></div>
  <div><b>Teléfono:</b> <%= user.getPhone() %></div>
  <div><b>Email:</b> <%= user.getEmail() %></div>
</div>

<h2 class="form-title">Avisos del usuario:</h2>
<ul class="ads-container">
  <div class="ads-list">
  <% if (ads.size() == 0) { %>
  <b>No creaste ningún aviso todavía. <a href="/sell.jsp">Creá uno ahora!</a></b>
  <% } else {
      for (Ad ad : ads) {
        String sold = ad.getSold() ? "Vendido" : "No vendido";
  %>
    <li>
      <a href="/ad.jsp?id=<%= ad.getId() %>">
        <div class="image-container"><img alt="Image for Ad <%= ad.getName() %>" src="<%= ad.getImage() %>" /></div>
        <div class="ad-container">
          <div class="ad-name"><%= ad.getName() %></div>
          <div class="ad-price">$<%= ad.getPrice() %></div>
          <div class="ad-date"><%= ad.getDate() %></div>
          <div><%= sold %></div>
        </div>
      </a>
      <div class="actions">
        <a class="button" href="/sell.jsp?id=<%= ad.getId() %>">Editar</a>
        <a class="button" href="/deleteAd.jsp?id=<%= ad.getId() %>">Eliminar</a>
        <% if (!ad.getSold()) { %>
        <a class="button" href="/markAdAsSold.jsp?id=<%= ad.getId() %>">Marcar como vendido</a>
        <% } %>
      </div>
    </li>
  <%
  }}
  %>
  </div>
</ul>
<jsp:include page="./layout/footer.jsp" />