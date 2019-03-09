package data;

import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.AppDataException;

public class DataUser implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataUser() {}

  public void add(User user) {
    ResultSet rs = null;
    PreparedStatement stmt = null;

    try {
      // $query = "SELECT * FROM user WHERE username='$username'";
      // $userResult = mysqli_query($link, $query);
      // $count = mysqli_num_rows($userResult);
      // if ($count == 1) {
      //   $error = "El usuario ya está en uso.";
      // } else {
      //   $query = "INSERT INTO user (username, password, name, phone, email) VALUES ('$username', '$hash', '$name', '$phone', '$email')";
      //   if (mysqli_query($link, $query)) {
      //     $_SESSION['username'] = $username;
      //     $_SESSION['id'] = mysqli_insert_id($link);
      //     $_SESSION['email'] = $email;
      //     header("location: profile.php");
      //   } else {
      //     $error = "Hubo un error al intentar crear el usuario.";
      //   }
      // }
     
      stmt = FactoryConnection.getInstancia().getConn().prepareStatement(
        "insert into user(dni,nombre, apellido,habilitado)" + " values(?,?,?,?)",
        PreparedStatement.RETURN_GENERATED_KEYS
      );

      stmt.setInt(1, user.getDni());
      stmt.setString(2, user.getNombre());
      stmt.setString(3, user.getApellido());
      stmt.setBoolean(4, user.isHabilitado());
      stmt.execute();

      // after executing the insert use the following lines to retrieve the id
      rs = stmt.getGeneratedKeys();
      if (rs != null && rs.next()) {
        user.setId(rs.getInt(1));
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (AppDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        FactoryConnection.getInstancia().releaseConn();
      } catch (AppDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public User getById(User per) {
    User user = null;

    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = FactoryConnection.getInstancia().getConn()
          .prepareStatement("select id, dni, nombre, apellido, habilitado from Users where dni=?");
      stmt.setInt(1, per.getDni());
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        user = new User();
        user.setId(rs.getInt("id"));
        user.setDni(rs.getInt("dni"));
        user.setNombre(rs.getString("nombre"));
        user.setApellido(rs.getString("apellido"));
        user.setHabilitado(rs.getBoolean("habilitado"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (AppDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        FactoryConnection.getInstancia().releaseConn();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (AppDataException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return user;
  }
}