<%@page import="entity.Category" %>
<%@page import="entity.Subcategory" %>
<jsp:include page="/layout/header.jsp" />
<jsp:include page="/css/index.css"/>
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
    Category[] categories = request.setParameter("categories", categories);
    for (Category category : categories) {
  %>
      <div class="category">
        <div class="category-title"><%= category.getName() %></div>
        <%
          Subcategory[] subcategories = category.getSubcategories()
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
<jsp:include page="/layout/footer.jsp" />