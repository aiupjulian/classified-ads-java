package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;
import util.ApplicationException;

@WebServlet(urlPatterns = { "/sell", "/sell.jsp" })
public class Sell extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Sell() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      try {
        String stringAdId = request.getParameter("id");
        if (stringAdId != null) {
          Integer adId = Integer.parseInt(request.getParameter("id"));
          AdController adController = new AdController();
          Ad ad = adController.getById(adId);
          request.setAttribute("ad", ad);
        }
        CategoryController categoryController = new CategoryController();
        ArrayList<Category> categories = categoryController.getAllWithSubcategories();
        request.setAttribute("categories", categories);
        StateController stateController = new StateController();
        ArrayList<State> states = stateController.getAllWithCities();
        request.setAttribute("states", states);
        request.getRequestDispatcher("/WEB-INF/jsp/sell.jsp").forward(request, response);
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    } else {
      response.sendRedirect("/login.jsp");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String error = "";
    String username = request.getParameter("username");
    if (username == "") {
      error = "Por favor complete todos los campos requeridos.";
      request.setAttribute("error", error);
      request.getRequestDispatcher("/WEB-INF/jsp/sell.jsp").forward(request, response);
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
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    }
  }
}
