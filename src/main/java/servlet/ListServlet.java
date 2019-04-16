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
public class ListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public ListServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HashMap<String,String> queryMap = new HashMap<String,String>();
    for (Map.Entry<String,String[]> param : request.getParameterMap().entrySet()) {
      if (request.getParameter(param.getKey()) != null && request.getParameter(param.getKey()) != "")
        queryMap.put(param.getKey(), request.getParameter(param.getKey()));
    }
    try {
      AdController adController = new AdController();
      DataAdsPages dataAdsPages = adController.getAllByQuery(queryMap);
      ArrayList<Ad> ads = new ArrayList<Ad>();
      ads = dataAdsPages.getAds();
      Integer pages = dataAdsPages.getPages();
      request.setAttribute("ads", ads);
      request.setAttribute("pages", pages);
      CategoryController categoryController = new CategoryController();
      ArrayList<Category> categories = categoryController.getAllWithSubcategories();
      request.setAttribute("categories", categories);
      StateController stateController = new StateController();
      ArrayList<State> states = stateController.getAllWithCities();
      request.setAttribute("states", states);
      request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e.getMessage());
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    super.doPost(request, response);
  }
}