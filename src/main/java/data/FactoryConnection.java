package data;

import java.sql.*;
import util.AppDataException;

public class FactoryConnection {
  private String dbDriver = "org.postgresql.jdbc.Driver";
  private String host = "localhost";
  private String port = "3306";
  private String user = "root";
  private String pass = "root";
  private String db = "classified_ads";
  private String dbType = "postgresql";

  private Connection conn;
  private int cantConn = 0;

  private FactoryConnection() throws AppDataException {
    try {
      Class.forName(dbDriver);
    } catch (ClassNotFoundException e) {
      throw new AppDataException(e, "Error del Driver JDBC");
    }
  }

  private static FactoryConnection instancia;

  public static FactoryConnection getInstancia() throws AppDataException {
    if (instancia == null) {
      instancia = new FactoryConnection();
    }
    return instancia;
  }

  public Connection getConn() {
    try {
      if (conn == null || conn.isClosed()) {
        conn = DriverManager.getConnection(
          "jdbc:" + dbType + "://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pass
        );
        cantConn++;
      }
    } catch (SQLException e) {
      new AppDataException(e, "Error al conectar a la DB");
    }
    return conn;
  }

  public void releaseConn() throws AppDataException {
    try {
      cantConn--;
      if (cantConn == 0) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new AppDataException(e, "Error al cerrar conexión");
    }
  }
}