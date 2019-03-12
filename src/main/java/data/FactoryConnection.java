package data;

import java.sql.*;
import util.ApplicationException;

public class FactoryConnection {
  private String dbDriver = "org.postgresql.jdbc.Driver";
  private String host = "localhost";
  private String port = "3306";
  private String user = "root";
  private String pass = "root";
  private String db = "classified_ads";
  private String dbType = "postgresql";

  private Connection conn;
  private int connCount = 0;

  private FactoryConnection() throws ApplicationException {
    try {
      Class.forName(dbDriver);
    } catch (ClassNotFoundException e) {
      throw new ApplicationException(e, "Error del Driver JDBC");
    }
  }

  private static FactoryConnection instance;

  public static FactoryConnection getInstance() throws ApplicationException {
    if (instance == null) {
      instance = new FactoryConnection();
    }
    return instance;
  }

  public Connection getConn() {
    try {
      if (conn == null || conn.isClosed()) {
        conn = DriverManager.getConnection(
          "jdbc:" + dbType + "://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pass
        );
        connCount++;
      }
    } catch (SQLException e) {
      new ApplicationException(e, "Error al conectar a la DB");
    }
    return conn;
  }

  public void releaseConn() throws ApplicationException {
    try {
      connCount--;
      if (connCount == 0) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new ApplicationException(e, "Error al cerrar conexión");
    }
  }
}