package data;

import java.io.Serializable;
import java.sql.*;

import entity.User;
import util.ApplicationException;

public class DataUser implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataUser() {}

  public User getByUsername(String username) throws ApplicationException {
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
      throw new ApplicationException(e, "Ocurrió un error al consultar la base de datos.");
    } finally {
      try {
        if (rs!=null) rs.close();
        if (stmt!=null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
        throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
      }
    }
    return user;
  }

  public User add(User user) throws ApplicationException {
    ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      boolean userAlreadyExists = getByUsername(user.getUsername()) != null;
      if (userAlreadyExists) {
        user = null;
      } else {
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
          user.setAdmin(false);
        }
      }
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar crear el usuario.");
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
        throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
      }
    }
    return user;
  }
}