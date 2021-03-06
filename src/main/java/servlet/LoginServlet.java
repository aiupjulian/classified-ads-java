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

@WebServlet(urlPatterns = {"/login", "/login.jsp"})
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      response.sendRedirect("/profile.jsp");
    } else {
      request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
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
      request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    } else {
      UserController userController = new UserController();
      try {
        User user = userController.validateUser(username, password);
        if (user != null) {
          request.getSession().setAttribute("user", user);
          response.sendRedirect("/profile.jsp");
        } else {
          request.setAttribute("error", "Usuario o contraseña inválidos.");
          request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    }
  }
}
