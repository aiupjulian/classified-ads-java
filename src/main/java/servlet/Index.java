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
import util.ApplicationException;

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
      throw new ApplicationException(null, "Test para ver error");
      request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    } catch (ApplicationException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}