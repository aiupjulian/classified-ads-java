package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

@WebServlet(urlPatterns = {"/login", "/login.jsp"})
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Login() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("username") != null) {
      request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String error = "";
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if (username == null) {
      error = "Por favor ingrese su usuario.";
    } else if (password == null) {
      error = "Por favor ingrese su contraseña.";
    }
    if (error != "") {
      request.setAttribute("error", error);
      request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    } else {

    }
  }
}


// <%
// if (!isset($error)) {
//   $query = "SELECT * FROM user WHERE username='$username'";
//   $userResult = mysqli_query($link, $query);
//   $user = mysqli_fetch_array($userResult, MYSQLI_ASSOC);
//   $count = mysqli_num_rows($userResult);

//   if ($count == 1 && password_verify($password, $user["password"])) {
//     $_SESSION['username'] = $username;
//     $_SESSION['id'] = $user["id"];
//     $_SESSION['admin'] = $user["admin"] === "1";
//     $_SESSION['email'] = $user["email"];
//     header("location: profile.php");
//   } else {
//     $error = "Usuario o contraseña inválidos.";
//   }
// }
// %>