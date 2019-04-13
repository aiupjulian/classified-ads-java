<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="entity.User" %>
<%@page import="entity.Ad" %>
<% User user = (User)session.getAttribute("user"); %>
<%
	ArrayList<Ad> ads = new ArrayList<Ad>();
	ads = (ArrayList<Ad>)request.getAttribute("ads");
%>
<jsp:include page="./layout/header.jsp" />
<style><%@ include file="../../css/profile.css" %></style>
<h2 class="form-title">Listado</h2>
<div class="list-container">
  <form action="" method="get" class="filters" autocomplete="off">
    <label for="name">Nombre del aviso:</label>
    <input type="text" name="name" maxlength="15" value="<?php echo isset($_GET['name']) ? $_GET['name'] : '' ?>">
    <fieldset>
      <legend>Precio:</legend>
      <input type="number" name="price1" maxlength="11" placeholder="Desde" value="<?php echo isset($_GET['price1']) ? $_GET['price1'] : '' ?>">
      <input type="number" name="price2" maxlength="11" placeholder="Hasta" value="<?php echo isset($_GET['price2']) ? $_GET['price2'] : '' ?>">
    </fieldset>
    <label for="city">Ciudad:</label>
    <select name="city">
      <?php if (empty($_GET["city"])) { ?>
        <option disabled selected value>Seleccione una ciudad</option>
      <?php
      }
      $statesQuery = "SELECT * FROM state";
      $statesResult = mysqli_query($link, $statesQuery);
      while ($state = mysqli_fetch_array($statesResult, MYSQLI_ASSOC)) {
      ?>
        <optgroup label="<?php echo $state['name']; ?>">
        <?php
        $citiesQuery = "SELECT * FROM city where state_id=" . $state['id'];
        $citiesResult = mysqli_query($link, $citiesQuery);
        while ($city = mysqli_fetch_array($citiesResult, MYSQLI_ASSOC)) {
        ?>
          <option value=<?php echo $city['id']; ?> <?php echo (!empty($_GET['city']) && $_GET['city'] == $city['id']) ? 'selected="selected"' : ''; ?>>
            <?php echo $city['name'] ?>
          </option>
        <?php } ?>
        </optgroup>
      <?php
      }
      ?>
    </select>
    <label for="subcategory">Subcategorías:</label>
    <select name="subcategory">
      <?php if (empty($_GET["subcategory"])) { ?>
        <option disabled selected value>Seleccione una subcategoría</option>
      <?php
      }
      $categoryQuery = "SELECT * FROM category";
      $categoryResult = mysqli_query($link, $categoryQuery);
      while ($category = mysqli_fetch_array($categoryResult, MYSQLI_ASSOC)) {
      ?>
        <optgroup label="<?php echo $category['name']; ?>">
        <?php
        $subcategoriesQuery = "SELECT * FROM subcategory where category_id=" . $category['id'];
        $subcategoriesResult = mysqli_query($link, $subcategoriesQuery);
        while ($subcategory = mysqli_fetch_array($subcategoriesResult, MYSQLI_ASSOC)) {
        ?>
          <option value=<?php echo $subcategory['id']; ?> <?php echo (!empty($_GET['subcategory']) && $_GET['subcategory'] == $subcategory['id']) ? 'selected="selected"' : ''; ?>>
            <?php echo $subcategory['name'] ?>
          </option>
        <?php } ?>
        </optgroup>
      <?php
      }
      ?>
    </select>
    <button class="button">Buscar</button>
  </form>
  <ul class="ads-container">
    <div class="ads-list">
    <?php
    if ($count == 0) {
    ?>
    <div style="text-align: center;">No hay avisos para mostrar con esos filtros.</div>
    <?php
    }
    while ($ad = mysqli_fetch_array($adsResult, MYSQLI_ASSOC)) {
    ?>
      <li>
        <a href="<?php echo "/ad.php?id=" . $ad['id'] ?>">
          <div class="image-container"><img alt="<?php echo "Image for Ad " . $ad['name']; ?>" src="<?php echo "images/uploaded/" . $ad['image']; ?>" /></div>
          <div class="ad-container">
            <div class="ad-name"><?php echo $ad['name']; ?></div>
            <div class="ad-user-name">
              Creado por: <?php echo $ad['user_name']; ?>
            </div>
            <div class="ad-price">$<?php echo $ad['price']; ?></div>
            <div class="ad-date"><?php echo $ad['date']; ?></div>
          </div>
        </a>
        <?php if(isset($_SESSION["admin"]) && $_SESSION["admin"])  { ?>
          <div class="ad-delete-admin"><a href="<?php echo "/deleteAd.php?id=" . $ad['id'] ?>">Eliminar este aviso</a></div>
        <?php } ?>
      </li>
    <?php
    }
    close($link);
    ?>
    </div>
    <div class="ads-pagination">
      <?php
      if ($pages > 1) {
        for ($i = 1; $i <= $pages; $i++) {
          $query = $_GET;
          $query['page'] = $i;
          $query_result = http_build_query($query);
          if ($page == $i) echo "<span>" . $page . "</span>";
          else echo "<a href='list.php?" . $query_result ."'>" . $i . "</a>";
        }
      }
      ?>
    </div>
  </ul>
</div>
<jsp:include page="./layout/footer.jsp" />