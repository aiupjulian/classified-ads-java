<jsp:include page="/layout/header.jsp" />
<%
if (session.getAttribute("username")) {
  header("location: profile.php");
}
unset($error);

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  require_once(LIBRARY_PATH . "/databaseFunctions.php");
  $link;
  connect($link);

  $username = mysqli_real_escape_string($link, $_POST['username']);
  $password = mysqli_real_escape_string($link, $_POST['password']);

  if ($username == '') {
    $error = 'Por favor ingrese su usuario.';
  } else if ($password == '') {
    $error = 'Por favor ingrese su contraseña.';
  }
  if (!isset($error)) {
    $query = "SELECT * FROM user WHERE username='$username'";
    $userResult = mysqli_query($link, $query);
    $user = mysqli_fetch_array($userResult, MYSQLI_ASSOC);
    $count = mysqli_num_rows($userResult);

    if ($count == 1 && password_verify($password, $user["password"])) {
      $_SESSION['username'] = $username;
      $_SESSION['id'] = $user["id"];
      $_SESSION['admin'] = $user["admin"] === "1";
      $_SESSION['email'] = $user["email"];
      mysqli_free_result($userResult);
      close($link);
      header("location: profile.php");
    } else {
      $error = "Usuario o contraseña inválidos.";
    }
  }
}
%>
<h2 class="form-title">Login</h2>
<form action="" method="post" class="form">
  <label for="username">Usuario:<span class="required"> (*)</span></label>
  <input type="text" name="username" maxlength="15" required />
  <label for="password">Contraseña:<span class="required"> (*)</span></label>
  <input type="password" name="password" maxlength="30" required />
  <button class="button">Ingresar</button>
  <% if (isset($error)) { %>
    <div class="error"><% echo $error; %></div>
  <% } %>
</form>
<jsp:include page="/layout/footer.jsp" />