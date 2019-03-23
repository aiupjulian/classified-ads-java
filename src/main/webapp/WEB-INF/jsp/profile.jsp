<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="./layout/header.jsp" />
<style><%@ include file="../../css/profile.css" %></style>
<?php
if (!isset($_SESSION['username'])) {
  header("location: login.php");
}
require_once(LIBRARY_PATH . "/databaseFunctions.php");
$link;
connect($link);
$user_id = mysqli_real_escape_string($link, $_SESSION['id']);
$query = "SELECT * FROM user WHERE user.id = '$user_id'";
$userResult = mysqli_query($link, $query);
$user = mysqli_fetch_array($userResult, MYSQLI_ASSOC);
$query = "SELECT * FROM ad WHERE ad.user_id = '$user_id'";
$adsResult = mysqli_query($link, $query);
?>

<h2 class="form-title">Datos del usuario:</h2>
<div class="form">
  <div><b>Nombre:</b> <?php echo $user['name']; ?></div>
  <div><b>Teléfono:</b> <?php echo $user['phone']; ?></div>
  <div><b>Email:</b> <?php echo $user['email']; ?></div>
</div>

<h2 class="form-title">Avisos del usuario:</h2>
<ul class="ads-container">
  <div class="ads-list">
  <?php
  if (mysqli_num_rows($adsResult) == 0) {
  ?>
  <b>No creaste ningún aviso todavía. <a href="/sell.php">Creá uno ahora!</a></b>
  <?php
  }
  while ($ad = mysqli_fetch_array($adsResult, MYSQLI_ASSOC)) {
  ?>
    <li>
      <a href="<?php echo "/ad.php?id=" . $ad['id'] ?>">
        <div class="image-container"><img alt="<?php echo "Image for Ad " . $ad['name']; ?>" src="<?php echo "images/uploaded/" . $ad['image']; ?>" /></div>
        <div class="ad-container">
          <div class="ad-name"><?php echo $ad['name']; ?></div>
          <div class="ad-price">$<?php echo $ad['price']; ?></div>
          <div class="ad-date"><?php echo $ad['date']; ?></div>
          <div><?php echo $ad['sold'] ? 'Vendido' : 'No vendido'; ?></div>
        </div>
      </a>
      <div class="actions">
        <a class="button" href="/sell.php?id=<?php echo $ad['id']; ?>">Editar</a>
        <a class="button" href="/deleteAd.php?id=<?php echo $ad['id']; ?>">Eliminar</a>
        <?php if (!$ad['sold']) { ?>
        <a class="button" href="/markAdAsSold.php?id=<?php echo $ad['id']; ?>">Marcar como vendido</a>
        <?php } ?>
      </div>
    </li>
  <?php
  }
  close($link);
  ?>
  </div>
</ul>
<jsp:include page="./layout/footer.jsp" />