package servlet;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.*;
import controller.*;
import util.*;

@WebServlet(urlPatterns = { "/ad", "/ad.jsp" })
public class AdServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String USER_NAME = "***";
  private static String PASSWORD = "***";

  public AdServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      AdController adController = new AdController();
      Ad ad = adController.getById(Integer.parseInt(request.getParameter("id")));
      request.setAttribute("ad", ad);
      CommentController commentController = new CommentController();
      ArrayList<Comment> comments = commentController.getAllByAdId(Integer.parseInt(request.getParameter("id")));
      request.setAttribute("comments", comments);
      request.getRequestDispatcher("/WEB-INF/jsp/ad.jsp").forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
      request.getRequestDispatcher("/WEB-INF/jsp/ad.jsp").forward(request, response);
    } else {
      try {
        User user = (User)session.getAttribute("user");
        String text = request.getParameter("text");
        if (request.getParameter("email") != null) {
          if (text == null || text == "") {
            request.setAttribute("emailError", "Por favor complete el mensaje.");
            request.getRequestDispatcher("/WEB-INF/jsp/ad.jsp").forward(request, response);
          } else {
            AdController adController = new AdController();
            Ad ad = adController.getById(Integer.parseInt(request.getParameter("id")));
            String from = USER_NAME;
            String pass = PASSWORD;
            String[] to = { ad.getUser().getEmail() }; // list of recipient email addresses
            String subject = "El usuario "+user.getUsername()+" ha ofertado en el aviso "+ad.getName();
            String body = text+"\r\n\r\nUsuario que oferta: "+user.getName()+"\r\nSu telefono: "+user.getPhone()+"\r\nSu email: "+user.getEmail();
            sendFromGMail(from, pass, to, subject, body);
            response.sendRedirect("/ad.jsp?id=" + ad.getId());
          }
        } else if (request.getParameter("comment") != null) {
          if (text == null || text == "") {
            request.setAttribute("commentError", "Por favor complete el mensaje.");
            request.getRequestDispatcher("/WEB-INF/jsp/ad.jsp").forward(request, response);
          } else {
            Comment comment = new Comment();
            comment.setText(text);
            comment.setUser(user);
            Ad ad = new Ad();
            ad.setId(Integer.parseInt(request.getParameter("id")));
            comment.setAd(ad);
            CommentController commentController = new CommentController();
            commentController.createComment(comment);
            response.sendRedirect("/ad.jsp?id=" + ad.getId());
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
    Properties props = System.getProperties();
    String host = "smtp.gmail.com";
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.user", from);
    props.put("mail.smtp.password", pass);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");

    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);

    try {
        message.setFrom(new InternetAddress(from));
        InternetAddress[] toAddress = new InternetAddress[to.length];

        // To get the array of addresses
        for( int i = 0; i < to.length; i++ ) {
            toAddress[i] = new InternetAddress(to[i]);
        }

        for( int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }

        message.setSubject(subject);
        message.setText(body);
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
    catch (AddressException ae) {
        ae.printStackTrace();
    }
    catch (MessagingException me) {
        me.printStackTrace();
    }
  }
}