package servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;
import entity.*;
import util.ApplicationException;

@WebServlet(urlPatterns = {"/profile", "/profile.jsp"})
public class Profile extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Profile() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      try {
        AdController adController = new AdController();
        ArrayList<Ad> ads = new ArrayList<Ad>();
        ads = adController.getAllByUser(((User)session.getAttribute("user")).getId());
        request.setAttribute("ads", ads);
        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      response.sendRedirect("/login.jsp");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO
  }
}
