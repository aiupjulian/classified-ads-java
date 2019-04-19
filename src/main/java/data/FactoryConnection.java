package data;

import java.net.URI;
import java.sql.*;
import util.ApplicationException;

public class FactoryConnection {
  private String dbDriver = "org.postgresql.Driver";

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
        URI dbUri = new URI("psql");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        conn = DriverManager.getConnection(dbUrl, username, password);
        connCount++;
      }
    } catch (Exception e) {
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