package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;
import util.ApplicationException;

@WebServlet(urlPatterns = {"/register", "/register.jsp"})
public class Register extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Register() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
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
      request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    } else {
      UserController userController = new UserController();
      try {
        User user = userController.validateUser(username, password);
        if (user != null) {
          request.getSession().setAttribute("user", user);
          request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
        } else {
          request.setAttribute("error", "Usuario o contraseña inválidos.");
          request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}


// if ($_SERVER["REQUEST_METHOD"] == "POST") {
//   $username = mysqli_real_escape_string($link, $_POST['username']);
//   $password = mysqli_real_escape_string($link, $_POST['password']);
//   $name = mysqli_real_escape_string($link, $_POST['name']);
//   $phone = mysqli_real_escape_string($link, $_POST['phone']);
//   $email = mysqli_real_escape_string($link, $_POST['email']);
//   $hash = password_hash($password, PASSWORD_BCRYPT);
  
//   if ($username=='' || $password=='' || $name=='' || $phone=='' || $email=='') {
//     $error = 'Por favor complete todos los campos requeridos.';
//   }
//   if (!isset($error)) {
//     $query = "SELECT * FROM user WHERE username='$username'";
//     $userResult = mysqli_query($link, $query);

//     $count = mysqli_num_rows($userResult);

//     if ($count == 1) {
//       $error = "El usuario ya está en uso.";
//     } else {
//       $query = "INSERT INTO user (username, password, name, phone, email) VALUES ('$username', '$hash', '$name', '$phone', '$email')";
//       if (mysqli_query($link, $query)) {
//         $_SESSION['username'] = $username;
//         $_SESSION['id'] = mysqli_insert_id($link);
//         $_SESSION['email'] = $email;
//         header("location: profile.php");
//       } else {
//         $error = "Hubo un error al intentar crear el usuario.";
//       }
//     }
//     close($link);
//   }
// }
// ?>