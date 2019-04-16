<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Integer" %>
<%@page import="entity.*" %>
<%
User user = null;
if (session.getAttribute("user") != null) {
  user = (User)session.getAttribute("user");
}
Ad ad = (Ad)request.getAttribute("ad");
ArrayList<Comment> comments = (ArrayList<Comment>)request.getAttribute("comments");
%>
<jsp:include page="./layout/header.jsp" />
<style><%@ include file="../../css/ad.css" %></style>
<h2 class="ad-title"><%= ad.getName() %></h2>
<div class="ad-container">
  <div class="ad-image">
    <img alt="Image for Ad <%= ad.getName() %>" src="<%= ad.getImage() %>" />
  </div>
  <div class="ad-details">
    <div><b>Descripción:</b> <%= ad.getDescription() %></div>
    <div><b>Precio:</b> $<%= ad.getPrice() %></div>
    <div><b>Categoría:</b> <%= ad.getSubcategory().getCategory().getName() %></div>
    <div><b>Subcategoría:</b> <%= ad.getSubcategory().getName() %></div>
    <div><b>Ciudad:</b> <%= ad.getCity().getName() %>, <%= ad.getCity().getState().getName() %></div>
    <div><b>Fecha de creación:</b> <%= ad.getDate() %></div>
    <div><b>Usuario:</b> <%= ad.getUser().getName() %></div>
    <div><b>Teléfono:</b> <%= ad.getUser().getPhone() %></div>
    <% if (!ad.getSold() && user != null && user.getId() != ad.getUser().getId()) { %>
      <form action="" method="post" class="form">
        <input type="hidden" name="email" />
        <label for="text">Mensaje de oferta:<span class="required"> (*)</span></label>
        <textarea type="text" name="text" rows="5" cols="30" maxlength="200" required placeholder="Oferta"></textarea>
        <button class="button">Enviar</button>
        <% if (request.getAttribute("emailError") != null) { %>
          <div class="error"><%= request.getAttribute("emailError") %></div>
        <% } %>
      </form>
    <% } %>
  </div>
</div>
<h2 class="comments-title">Comentarios</h2>
<div class="comments-container">
  <ul class="comments-list">
    <% if (comments.size() == 0) { %>
    <b>No hay comentarios en este aviso.</b>
    <% } for (Comment comment : comments) { %>
      <li>
      <% if (ad.getUser().getId() == comment.getUser().getId()) { %>
        <span class="comment-user-name comment-ad-owner">
      <% } else { %>
        <span class="comment-user-name">
      <% } %>
          <%= comment.getUser().getName() %>:
        </span>
        <span class="comment-text">"<%= comment.getText() %>"</span>
        <span class="comment-date"><%= comment.getDate() %></span>
      </li>
    <% } %>
  </ul>
  <% if (user != null) { %>
    <form action="" method="post" class="form">
      <input type="hidden" name="comment" />
      <label for="text">Mensaje de comentario:<span class="required"> (*)</span></label>
      <textarea type="text" name="text" rows="5" cols="30" maxlength="200" required placeholder="Comentario"></textarea>
      <button class="button">Enviar</button>
      <% if (request.getAttribute("commentError") != null) { %>
        <div class="error"><%= request.getAttribute("commentError") %></div>
      <% } %>
    </form>
  <% } %>
</div>
<jsp:include page="./layout/footer.jsp" />