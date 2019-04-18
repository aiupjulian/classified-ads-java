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

@WebServlet(urlPatterns = {"/deleteState", "/deleteState.jsp"})
public class DeleteStateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DeleteStateServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String stringStateId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin() && stringStateId != null) {
      try {
        Integer stateId = Integer.parseInt(stringStateId);
        StateController stateController = new StateController();
        stateController.delete(stateId);
        response.sendRedirect("/states.jsp");
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
