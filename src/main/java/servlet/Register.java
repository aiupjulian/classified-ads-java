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
      response.sendRedirect("/profile.jsp");
    } else {
      request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String error = "";
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    String email = request.getParameter("email");
    if (username == "" || password == "" || name == "" || phone == "" || email == "") {
      error = "Por favor complete todos los campos requeridos.";
      request.setAttribute("error", error);
      request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    } else {
      UserController userController = new UserController();
      try {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user = userController.registerUser(user);
        if (user != null) {
          request.getSession().setAttribute("user", user);
          response.sendRedirect("/profile.jsp");
        } else {
          request.setAttribute("error", "El usuario ya está en uso.");
          request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
