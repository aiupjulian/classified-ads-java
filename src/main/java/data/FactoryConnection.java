package data;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import util.ApplicationException;

public class FactoryConnection {
  private String dbDriver = "org.postgresql.Driver";
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
        URI dbUri = new URI("postgres://udfgkvxqldcncl:d89f9d9222498d92d14d3afa04fc576a0d7e662b6d0bf4fdac29067f7acda065@ec2-23-21-171-25.compute-1.amazonaws.com:5432/d7vk8vamhhq921");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        conn = DriverManager.getConnection(dbUrl, username, password);
        connCount++;
      }
    } catch (SQLException e) {
      new ApplicationException(e, "Error al conectar a la DB");
    } catch (URISyntaxException e) {
      e.printStackTrace();
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