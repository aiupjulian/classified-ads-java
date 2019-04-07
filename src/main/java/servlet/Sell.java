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
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      String error = "";
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String price = request.getParameter("price");
      String cityId = request.getParameter("city");
      String subcategoryId = request.getParameter("subcategory");
      // calculate current date
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate localDate = LocalDate.now();
      String date = dtf.format(localDate);
      if (name == "" || description == "" || price == "" || cityId == "" || subcategoryId == "") {
        error = "Por favor complete todos los campos requeridos.";
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/sell.jsp").forward(request, response);
      } else {
        AdController adController = new AdController();
        try {
          Ad ad = new Ad();
          ad.setName(name);
          ad.setDescription(description);
          ad.setPrice(price);
          ad.setCity(phone);
          ad.setEmail(email);
          ad = adController.createAd(ad);
          request.getSession().setAttribute("ad", ad);
          response.sendRedirect("/ad.jsp"); // TODO: ADD adId
        } catch (Exception e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/login.jsp");
    }
  }
}
