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
@WebServlet(urlPatterns = {"/markAdAsSold", "/markAdAsSold.jsp"})
public class MarkAdAsSold extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public MarkAdAsSold() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String stringAdId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && stringAdId != null) {
      Integer adId = Integer.parseInt(stringAdId);
      Integer userId = ((User)session.getAttribute("user")).getId();
      AdController adController = new AdController();
      try {
        adController.markAdAsSold(adId, userId);
        response.sendRedirect("/profile.jsp");
      } catch (SQLException e) {
        throw new ServletException(e.getMessage());
      } catch (ApplicationException ae){
        throw new ServletException(ae.getMessage());
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
