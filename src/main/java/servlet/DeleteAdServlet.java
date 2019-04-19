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

@WebServlet(urlPatterns = {"/deleteAd", "/deleteAd.jsp"})
public class DeleteAdServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DeleteAdServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String stringAdId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && stringAdId != null) {
      try {
        Integer adId = Integer.parseInt(stringAdId);
        AdController adController = new AdController();
        adController.delete(adId, (User)session.getAttribute("user"));
        response.sendRedirect("/profile.jsp");
      } catch (Exception e){
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
