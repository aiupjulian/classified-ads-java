package servlet;

import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import entity.*;
@WebServlet(urlPatterns = {"/index", "/index.jsp"})
public class Index extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Index() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CategoryController categoryController = new CategoryController();
    ArrayList<Category> categories = new ArrayList<Category>();
    try {
      categories = categoryController.getAllWithSubcategories();
      request.setAttribute("categories", categories);
      request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e.getMessage());
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    super.doPost(request, response);
  }
}