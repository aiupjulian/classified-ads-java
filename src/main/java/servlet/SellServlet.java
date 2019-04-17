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
import com.uploadcare.api.Client;
import com.uploadcare.upload.FileUploader;
import com.uploadcare.upload.Uploader;
import com.uploadcare.upload.UploadFailureException;

@MultipartConfig()
@WebServlet(urlPatterns = { "/sell", "/sell.jsp" })
public class SellServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SellServlet() {
    super();
  }

  private static String getSubmittedFileName(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
      if (cd.trim().startsWith("filename")) {
        String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
        return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
      }
    }
    return null;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      try {
        String stringAdId = request.getParameter("id");
        if (stringAdId != null) {
          Integer adId = Integer.parseInt(request.getParameter("id"));
          AdController adController = new AdController();
          Ad ad = adController.getById(adId);
          request.setAttribute("ad", ad);
        }
        CategoryController categoryController = new CategoryController();
        ArrayList<Category> categories = categoryController.getAllWithSubcategories();
        request.setAttribute("categories", categories);
        StateController stateController = new StateController();
        ArrayList<State> states = stateController.getAllWithCities();
        request.setAttribute("states", states);
        request.getRequestDispatcher("/WEB-INF/jsp/sell.jsp").forward(request, response);
      } catch (Exception e) {
        throw new ServletException(e.getMessage());
      }
    } else {
      response.sendRedirect("/login.jsp");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      String error = "";
      String name = request.getParameter("name");
      String description = request.getParameter("description");
      String priceString = request.getParameter("price");
      String cityIdString = request.getParameter("city");
      String subcategoryIdString = request.getParameter("subcategory");
      if (name == "" || description == "" || priceString == "" || cityIdString == "" || subcategoryIdString == "") {
        error = "Por favor complete todos los campos requeridos.";
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/jsp/sell.jsp").forward(request, response);
      } else {
        AdController adController = new AdController();
        try {
          // Image upload
          String image = "";
          Part filePart = request.getPart("image");
          if (filePart.getSize() != 0) {
            String fileName = getSubmittedFileName(filePart);
            InputStream fileContent = filePart.getInputStream();
            byte[] buffer = new byte[fileContent.available()];
            fileContent.read(buffer);
            File targetFile = File.createTempFile(fileName, ".jpg");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            Client client = new Client("ce0c630573163927235e", "25907025b19c9fcf77ea");
            Uploader uploader = new FileUploader(client, targetFile);
            com.uploadcare.api.File file = uploader.upload().save();
            image = file.getOriginalFileUrl().toString();
          }

          Ad ad = new Ad();
          ad.setName(name);
          ad.setDescription(description);
          ad.setPrice(Integer.parseInt(priceString));
          City city = new City();
          city.setId(Integer.parseInt(cityIdString));
          ad.setCity(city);
          Subcategory subcategory = new Subcategory();
          subcategory.setId(Integer.parseInt(subcategoryIdString));
          ad.setSubcategory(subcategory);
          if (image != "") {
            ad.setImage(image);
          }
          String stringAdId = request.getParameter("id");
          if (stringAdId != null) {
            ad.setId(Integer.parseInt(request.getParameter("id")));
            adController.updateAd(ad);
          } else {
            if (image == "") {
              ad.setImage("https://ucarecdn.com/eae359c8-bbca-4ef8-88cd-bdaee854ecdf/nopic.jpg");
            }
            ad.setUser((User)session.getAttribute("user"));
            ad = adController.createAd(ad);
          }
          response.sendRedirect("/ad.jsp?id=" + ad.getId());
        } catch (ApplicationException | UploadFailureException e) {
          throw new ServletException(e.getMessage());
        }
      }
    } else {
      response.sendRedirect("/login.jsp");
    }
  }
}
