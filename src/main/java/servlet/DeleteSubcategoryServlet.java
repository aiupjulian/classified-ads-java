package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;

@WebServlet(urlPatterns = {"/deleteSubcategory", "/deleteSubcategory.jsp"})
public class DeleteSubcategoryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DeleteSubcategoryServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String stringSubcategoryId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin() && stringSubcategoryId != null) {
      try {
        Integer subcategoryId = Integer.parseInt(stringSubcategoryId);
        SubcategoryController subcategoryController = new SubcategoryController();
        subcategoryController.delete(subcategoryId);
        response.sendRedirect("/subcategories.jsp");
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
