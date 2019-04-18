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
@WebServlet(urlPatterns = { "/state", "/state.jsp" })
public class StateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public StateServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null && ((User)session.getAttribute("user")).getAdmin()) {
      try {
        String stringStateId = request.getParameter("id");
        if (stringStateId != null) {
          Integer stateId = Integer.parseInt(stringStateId);
          StateController stateController = new StateController();
          State state = stateController.getById(stateId);
          request.setAttribute("state", state);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/state.jsp").forward(request, response);
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
        request.getRequestDispatcher("/WEB-INF/jsp/state.jsp").forward(request, response);
      } else {
        try {
          StateController stateController = new StateController();
          State state = new State();
          state.setName(name);
          String stringStateId = request.getParameter("id");
          if (stringStateId != null) {
            state.setId(Integer.parseInt(stringStateId));
            stateController.update(state);
          } else {
            stateController.create(state);
          }
          response.sendRedirect("/states.jsp");
        } catch (Exception e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/index.jsp");
    }
  }
}
