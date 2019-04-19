package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;

@MultipartConfig()
@WebServlet(urlPatterns = { "/category", "/category.jsp" })
public class CategoryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public CategoryServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        String stringCategoryId = request.getParameter("id");
        if (stringCategoryId != null) {
          Integer categoryId = Integer.parseInt(stringCategoryId);
          CategoryController categoryController = new CategoryController();
          Category category = categoryController.getById(categoryId);
          request.setAttribute("category", category);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/category.jsp").forward(request, response);
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
      if (name == "") {
        error = "Por favor complete todos los campos requeridos.";
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/category.jsp").forward(request, response);
      } else {
        try {
          CategoryController categoryController = new CategoryController();
          Category category = new Category();
          category.setName(name);
          String stringCategoryId = request.getParameter("id");
          if (stringCategoryId != null) {
            category.setId(Integer.parseInt(stringCategoryId));
            categoryController.update(category);
          } else {
            categoryController.create(category);
          }
          response.sendRedirect("/categories.jsp");
        } catch (Exception e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }
}
