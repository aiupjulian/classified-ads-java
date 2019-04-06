<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.lang.String" %>
<%@page import="entity.Ad" %>
<%
String buttonText = "Crear";
if (request.getAttribute("ad") != null) {
  Ad ad = (Ad)request.getAttribute("ad");
  buttonText = "Editar";
}
%>
<jsp:include page="./layout/header.jsp" />
<h2 class="form-title">Crear Aviso</h2>
<form action="" method="post" class="form" enctype="multipart/form-data">
  <label for="name">Nombre del aviso:<span class="required"> (*)</span></label>
  <input type="text" name="name" maxlength="15" required <?php if(isset($name)) echo "value='$name'"; ?>>
  <label for="description">Descripción:<span class="required"> (*)</span></label>
  <input type="text" name="description" maxlength="60" required <?php if(isset($description)) echo "value='$description'"; ?>>
  <label for="price">Precio:<span class="required"> (*)</span></label>
  <input type="number" name="price" maxlength="11" required <?php if(isset($price)) echo "value='$price'"; ?>>
  <label for="city">Ciudad:<span class="required"> (*)</span></label>
  <select name="city" required>
    <?php
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
        <option value=<?php echo $city['id']; ?> <?php if(isset($city_id) && $city['id'] == $city_id) echo 'selected="selected"'; ?>>
          <?php echo $city['name'] ?>
        </option>
      <?php } ?>
      </optgroup>
    <?php
    }
    close($link);
    ?>
  </select>
  <label for="subcategory">Subcategoría:<span class="required"> (*)</span></label>
  <select name="subcategory" required>
    <?php
    $link;
    connect($link);
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
        <option value=<?php echo $subcategory['id']; ?> <?php if(isset($subcategory_id) && $subcategory['id'] == $subcategory_id) echo 'selected="selected"'; ?>>
          <?php echo $subcategory['name'] ?>
        </option>
      <?php } ?>
      </optgroup>
    <?php
    }
    close($link);
    ?>
  </select>
  <label for="image">Imágen:<span class="required"> (*)</span></label>
  <input type="hidden" name="MAX_FILE_SIZE" value="2000000">
  <input type="file" name="image" accept="image/png, image/jpeg" required>
  <button class="button"><%= buttonText %></button>
  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>
</form>
<jsp:include page="./layout/footer.jsp" />