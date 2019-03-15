<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="entity.Category" %>
<%@page import="entity.Subcategory" %>
<jsp:include page="./jsp/layout/header.jsp" />
<style><%@ include file="../css/index.css" %></style>
<div class="hero-container">
  <div class="hero"></div>
  <div class="hero-text">
    <h2>Bienvenido a Classified Ads!</h2>
    <h3>Donde podes comprar y vender lo que quieras</h3>
    <p><b>Por favor registrate para empezar a comprar y vender!</b></p>
    <p><b>Si querés simplemente podes revisar avisos entre las subcategorías.</b></p>
  </div>
</div>
<div class="categories-title">
  <h3>Categorías</h3>
  <a href="/list.php">Ver todas</a>
</div>
<div class="categories-list">
  <%
    ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories");
    for (Category category : categories) {
  %>
      <div class="category">
        <div class="category-title"><%= category.getName() %></div>
        <%
          ArrayList<Subcategory> subcategories = category.getSubcategories();
          for (Subcategory subcategory : subcategories) {
        %>
              <div class="subcategory">
                <a href="list.php?subcategory=<%= subcategory.getId() %>">
                  <%= subcategory.getName() %>
                </a>
              </div>
        <% } %>
        </div>
  <% } %>
</div>
<jsp:include page="./jsp/layout/footer.jsp" />