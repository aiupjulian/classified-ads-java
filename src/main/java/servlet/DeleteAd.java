package servlet;

import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import entity.*;
@WebServlet(urlPatterns = {"/deleteAd", "/deleteAd.jsp"})
public class DeleteAd extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DeleteAd() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    Int adId = request.getParameter("id");
    if (session != null && session.getAttribute("user") != null && adId != null) {
      AdController adController = new AdController();
      try {
        adController.getAllWithSubcategories();
        response.sendRedirect("/index.jsp");
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


<?php
if ($_GET['id'] && isset($_SESSION['username'])) {
  $user_id = mysqli_real_escape_string($link, $_SESSION['id']);
  $ad_id = mysqli_real_escape_string($link, $_GET['id']);
  $query = "SELECT * FROM ad WHERE id='$ad_id'";
  $adResult = mysqli_query($link, $query);
  $ad = mysqli_fetch_array($adResult, MYSQLI_ASSOC);
  $count = mysqli_num_rows($adResult);
  if ($count == 1 && ($ad['userId'] == $user_id || $_SESSION['admin'])) {
    $query = "DELETE FROM ad WHERE id='$ad_id'";
    echo mysqli_query($link, $query);
    if (mysqli_query($link, $query)) {
      header("location: profile.php");
    } else {
      echo mysqli_error($link);
      header("location: index.php");
    }
  } else {
    echo mysqli_error($link);
    header("location: index.php");
  }
} else {
  header("location: index.php");
}
?>