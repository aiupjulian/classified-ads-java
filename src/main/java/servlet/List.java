package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import entity.*;
import util.*;

@WebServlet(urlPatterns = {"/list", "/list.jsp"})
public class List extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public List() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HashMap<String,String> queryMap = new HashMap<String,String>();
    for (Map.Entry<String,String[]> param : request.getParameterMap().entrySet()) {
      if (request.getParameter(param.getKey()) != null) queryMap.put(param.getKey(), request.getParameter(param.getKey()));
    }
    try {
      AdController adController = new AdController();
      DataAdsPages dataAdsPages = adController.getAllByQuery(queryMap);
      ArrayList<Ad> ads = new ArrayList<Ad>();
      ads = dataAdsPages.getAds();
      Integer pages = dataAdsPages.getPages();
      System.out.println(ads.toString());
      System.out.println(pages.toString());
      request.setAttribute("ads", ads);
      request.setAttribute("pages", pages);
      request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String error = "";
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    String email = request.getParameter("email");
    if (username == "" || password == "" || name == "" || phone == "" || email == "") {
      error = "Por favor complete todos los campos requeridos.";
      request.setAttribute("error", error);
      request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    } else {
      UserController userController = new UserController();
      try {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user = userController.registerUser(user);
        if (user != null) {
          request.getSession().setAttribute("user", user);
          response.sendRedirect("/profile.jsp");
        } else {
          request.setAttribute("error", "El usuario ya está en uso.");
          request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}