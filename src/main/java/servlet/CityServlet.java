package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controller.*;
import entity.*;
import util.ApplicationException;

@MultipartConfig()
@WebServlet(urlPatterns = { "/city", "/city.jsp" })
public class CityServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public CityServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        String stringCityId = request.getParameter("id");
        if (stringCityId != null) {
          Integer cityId = Integer.parseInt(stringCityId);
          CityController cityController = new CityController();
          City city = cityController.getById(cityId);
          request.setAttribute("city", city);
        }
        StateController stateController = new StateController();
        ArrayList<State> states = stateController.getAllWithCities();
        request.setAttribute("states", states);
        request.getRequestDispatcher("/WEB-INF/jsp/city.jsp").forward(request, response);
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      String error = "";
      String name = request.getParameter("name");
      String stringStateId = request.getParameter("state");
      if (name == "" || stringStateId == "") {
        error = "Por favor complete todos los campos requeridos.";
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/city.jsp").forward(request, response);
      } else {
        try {
          Integer stateId = Integer.parseInt(stringStateId);
          CityController cityController = new CityController();
          City city = new City();
          city.setName(name);
          State state = new State();
          state.setId(stateId);
          city.setState(state);
          String stringCityId = request.getParameter("id");
          if (stringCityId != null) {
            city.setId(Integer.parseInt(stringCityId));
            cityController.update(city);
          } else {
            cityController.create(city);
          }
          response.sendRedirect("/cities.jsp");
        } catch (Exception e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }
}
