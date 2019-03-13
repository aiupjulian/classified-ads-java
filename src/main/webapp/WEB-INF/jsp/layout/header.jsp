<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Classified Ads</title>
    <link href="https://cdn.jsdelivr.net/npm/normalize.css@8.0.0/normalize.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" />
    <link href="css/variables.css" rel="stylesheet" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/header.css" rel="stylesheet" />
    <link href="css/footer.css" rel="stylesheet" />
  </head>
  <body>
    <div class="header">
      <div class="header-section">
        <h1><a href="index.jsp">Classified Ads</a></h1>
      </div>
      <form action="/list.jsp" method="get" class="header-section">
        <input
          type="text"
          name="name"
          maxlength="15"
          placeholder="Qué estas buscando?"
        />
        <button class="button">Buscar</button>
      </form>
      <div class="header-section">
        <a href="list.jsp">Listado</a>
        <% if (session.getAttribute("username") != null) { %>
          <a href="sell.jsp">Vender</a>
          <a href="profile.jsp">Perfil</a>
          <a href="logout.jsp">Logout</a>
        <% } else { %>
          <a href="register.jsp">Registro</a>
          <a href="login.jsp">Login</a>
        <% } %>
      </div>
    </div>
  </body>
</html>
