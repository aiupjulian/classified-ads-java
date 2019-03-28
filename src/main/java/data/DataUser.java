package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

import entity.User;
import util.ApplicationException;

public class DataUser implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataUser() {}

  public User getByUsername(String username) throws Exception {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    User user = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("SELECT * FROM classified_ads.user WHERE username=?");
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs != null && rs.next()){
        user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setAdmin(rs.getBoolean("admin"));
      }
    } catch (SQLException e) {
      throw e;
    } catch (ApplicationException ae){
      throw ae;
    } finally {
      try {
        if (rs!=null) rs.close();
        if (stmt!=null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
        throw e;
      }
    }
    return user;
  }

  public User add(User user) throws ApplicationException {
    ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "INSERT INTO classified_ads.user (username, password, name, phone, email) VALUES (?, ?, ?, ?, ?) RETURNING id"
      );
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getName());
      stmt.setString(4, user.getPhone());
      stmt.setString(5, user.getEmail());
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        user.setId(rs.getInt(1));
      } else {
        throw new ApplicationException("El usuario ya está en uso.??????");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(e, "Hubo un error al intentar crear el usuario.");
    } catch (ApplicationException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }
}