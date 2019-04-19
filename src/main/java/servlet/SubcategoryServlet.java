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
@WebServlet(urlPatterns = { "/subcategory", "/subcategory.jsp" })
public class SubcategoryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SubcategoryServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        String stringSubcategoryId = request.getParameter("id");
        if (stringSubcategoryId != null) {
          Integer subcategoryId = Integer.parseInt(stringSubcategoryId);
          SubcategoryController subcategoryController = new SubcategoryController();
          Subcategory subcategory = subcategoryController.getById(subcategoryId);
          request.setAttribute("subcategory", subcategory);
        }
        CategoryController categoryController = new CategoryController();
        ArrayList<Category> categories = categoryController.getAllWithSubcategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/jsp/subcategory.jsp").forward(request, response);
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
      String stringCategoryId = request.getParameter("category");
      if (name == "" || stringCategoryId == "") {
        error = "Por favor complete todos los campos requeridos.";
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/subcategory.jsp").forward(request, response);
      } else {
        try {
          Integer categoryId = Integer.parseInt(stringCategoryId);
          SubcategoryController subcategoryController = new SubcategoryController();
          Subcategory subcategory = new Subcategory();
          subcategory.setName(name);
          Category category = new Category();
          category.setId(categoryId);
          subcategory.setCategory(category);
          String stringSubcategoryId = request.getParameter("id");
          if (stringSubcategoryId != null) {
            subcategory.setId(Integer.parseInt(stringSubcategoryId));
            subcategoryController.update(subcategory);
          } else {
            subcategoryController.create(subcategory);
          }
          response.sendRedirect("/subcategories.jsp");
        } catch (Exception e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }
}
