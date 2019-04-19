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
@WebServlet(urlPatterns = { "/subcategories", "/subcategories.jsp" })
public class SubcategoriesServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SubcategoriesServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        SubcategoryController subcategoryController = new SubcategoryController();
        ArrayList<Subcategory> subcategories = subcategoryController.getAll();
        request.setAttribute("subcategories", subcategories);
        request.getRequestDispatcher("/WEB-INF/jsp/subcategories.jsp").forward(request, response);
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
