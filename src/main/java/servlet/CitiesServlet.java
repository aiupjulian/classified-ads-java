package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;
import util.ApplicationException;

@MultipartConfig()
@WebServlet(urlPatterns = { "/cities", "/cities.jsp" })
public class CitiesServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public CitiesServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        CityController cityController = new CityController();
        ArrayList<State> cities = cityController.getAll();
        request.setAttribute("cities", cities);
        request.getRequestDispatcher("/WEB-INF/jsp/cities.jsp").forward(request, response);
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    super.doPost(request, response);
  }
}
