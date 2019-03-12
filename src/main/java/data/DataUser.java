package data;

import java.io.Serializable;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

import entity.User;
import util.ApplicationException;

// -------------------------------------
// Check that an unencrypted password matches one that has
// previously been hashed
// if (BCrypt.checkpw(candidate, hashed))
// 	System.out.println("It matches");
// else
// 	System.out.println("It does not match");
// ---------------------------------------

public class DataUser implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataUser() {}

  public void add(User user) {
    ResultSet rs = null;
    PreparedStatement stmt = null;
    String error = "";

    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "SELECT * FROM user WHERE username=?"
      );
      stmt.setString(1, user.getUsername());
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        error = "El usuario ya está en uso.";
      } else {
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        stmt = FactoryConnection.getInstance().getConn().prepareStatement(
          "INSERT INTO user (username, password, name, phone, email) VALUES (?, ?, ?, ?, ?)"
          PreparedStatement.RETURN_GENERATED_KEYS
        );

        stmt.setString(1, user.getUsername());
        stmt.setString(2, hash);
        stmt.setString(3, user.getName());
        stmt.setString(4, user.getPhone());
        stmt.setString(5, user.getEmail());
        stmt.execute();

        // after executing the insert use the following lines to retrieve the id
        rs = stmt.getGeneratedKeys();
        if (rs != null && rs.next()) {
          user.setId(rs.getInt(1));
          HttpSession session = request.getSession();
          session.setAttribute("username", user.getUsername());
          session.setAttribute("id", user.getId());
          session.setAttribute("email", user.getEmail());
          // redirect to profile
        }
      }
// $error = "Hubo un error al intentar crear el usuario.";
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ApplicationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (ApplicationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public User getById(Int id) {
    User user = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "SELECT * FROM user WHERE id=?"
      );
      stmt.setInt(1, id);
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        user = new User(
          id,
          rs.getString("username"),
          rs.getString("password"),
          rs.getString("phone"),
          rs.getString("email"),
          rs.getString("name"),
          rs.getBoolean("admin")
        );
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ApplicationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ApplicationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return user;
  }
}