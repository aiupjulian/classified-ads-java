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

// <?php
// session_start();
// if ($_GET['id'] && isset($_SESSION['username'])) {
//   require_once(realpath(dirname(__FILE__) . "/../resources/config.php"));
//   require_once(LIBRARY_PATH . "/databaseFunctions.php");
//   $link;
//   connect($link);
//   $user_id = mysqli_real_escape_string($link, $_SESSION['id']);
//   $ad_id = mysqli_real_escape_string($link, $_GET['id']);
//   $query = "SELECT * FROM ad WHERE id='$ad_id' AND user_id='$user_id'";
//   $adResult = mysqli_query($link, $query);
//   $count = mysqli_num_rows($adResult);
//   $ad = mysqli_fetch_array($adResult, MYSQLI_ASSOC);
//   if ($count == 1) {
//     $query = "UPDATE ad SET sold=1 WHERE id='$ad_id'";
//     if (mysqli_query($link, $query)) {
//       header("location: profile.php");
//     } else {
//       header("location: index.php");
//     }
//   } else {
//     header("location: index.php");
//   }
// } else {
//   header("location: index.php");
// }
// ?>
