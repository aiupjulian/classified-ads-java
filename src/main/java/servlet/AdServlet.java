package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import controller.*;
import util.*;

@WebServlet(urlPatterns = {"/ad", "/ad.jsp"})
public class AdServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

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
    String text = request.getParameter("text");
    if (text == null || text == "") {
      request.setAttribute("emailError", "Por favor complete el mensaje.");
      request.getRequestDispatcher("/WEB-INF/jsp/ad.jsp").forward(request, response);
    }
    if (request.getParameter("email") != null) {

    }
    if (request.getParameter("comment") != null) {

    }
  }
}

//   // calculate current date
//   $date_array = getdate();
//   $date = $date_array['year'] . "-" . $date_array['mon'] . "-" . $date_array['mday'];
//   $user_id = $_SESSION['id'];
//   if (isset($_POST['email'])) {
//     if ($text == '') {
//        $email_error = 'Por favor complete el mensaje.';
//     }
//     if (!isset($email_error)) {
//       $new_text = str_replace('\r\n', '<br />', $text);
//       $message = $new_text . '<br /><br />Usuario que oferta: ' . $_SESSION['username'] . '<br />En la fecha: ' . $date . '<br />Email del usuario que oferta: ' . $_SESSION['email'];
//       $message = wordwrap($message, 70, "<br />");
//         date_default_timezone_set('Etc/UTC');
//         require_once(TEMPLATES_PATH . '/PHPMailer/PHPMailerAutoload.php');
//         $mail = new PHPMailer;
//         $mail->isSMTP();
//         $mail->Host = 'smtp.gmail.com'; // Which SMTP server to use.
//         $mail->Port = 587; // Which port to use, 587 is the default port for TLS security.
//         $mail->SMTPSecure = 'tls'; // Which security method to use. TLS is most secure.
//         $mail->SMTPAuth = true; // Whether you need to login. This is almost always required.
//         $mail->Username = "classifiedadsml@gmail.com"; // Your Gmail address.
//         $mail->Password = "Lima-Limon505"; // Your Gmail login password or App Specific Password.
//         $mail->setFrom('classifiedadsml@gmail.com', 'Classified Ads'); // Set the sender of the message.
//         $mail->addAddress($ad_user_email, $ad_user_name); // Set the recipient of the message.
//         $mail->Subject = 'El usuario ' .  $_SESSION['username'] . ' ha ofertado en el aviso ' . $name; // The subject of the message.
//         // Choose to send either a simple text email...
//         //$mail->Body = $message; // Set a plain text body.
//         // ... or send an email with HTML.
//         $mail->msgHTML($message);
//         // Optional when using HTML: Set an alternative plain text message for email clients who prefer that.
//         //$mail->AltBody = 'This is a plain-text message body'; 
//         // Optional: attach a file
//         // $mail->addAttachment('images/phpmailer_mini.png');
//         if ($mail->send()) {}
//         //if ($mail->send()) {
//         //    echo "Your message was sent successfully!";
//         //} else {
//         //    echo "Mailer Error: " . $mail->ErrorInfo;
//         //}
//       //if (!mail($ad_user_email, 'User ' .  $_SESSION['username'] . 'offered in ' . $name, $message)) {
//     // $email_error = "Hubo un error al intentar enviar el email.";
//       //}
//     }
//   }