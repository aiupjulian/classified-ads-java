package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;
import util.ApplicationException;

@WebServlet(urlPatterns = {"/deleteCategory", "/deleteCategory.jsp"})
public class DeleteCategoryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DeleteCategoryServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String stringCategoryId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin() && stringCategoryId != null) {
      try {
        Integer categoryId = Integer.parseInt(stringCategoryId);
        CategoryController categoryController = new CategoryController();
        categoryController.delete(categoryId);
        response.sendRedirect("/categories.jsp");
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
