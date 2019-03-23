package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.ApplicationException;

public class DataAd implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataAd() {}

  public ArrayList<Ad> getAllByUser(Integer userId) throws Exception {
    PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "SELECT classified_ads.ad.*, classified_ads.user.*, " +
        "classified_ads.subcategory.*, classified_ads.category.*, " +
        "classified_ads.city.*, classified_ads.state.* " +
        "FROM classified_ads.ad " +
        "INNER JOIN classified_ads.user ON classified_ads.ad.user_id=classified_ads.user.id " +
        "INNER JOIN classified_ads.subcategory ON classified_ads.ad.subcategory_id=classified_ads.subcategory.id " +
        "INNER JOIN classified_ads.category ON classified_ads.subcategory.category_id=classified_ads.category.id " +
        "INNER JOIN classified_ads.city ON classified_ads.ad.city_id=classified_ads.city.id " +
        "INNER JOIN classified_ads.state ON classified_ads.city.state_id=classified_ads.state.id " +
        "WHERE classified_ads.ad.user_id=?"
      );
      stmt.setInt(1, userId);
      rs = stmt.executeQuery();
			if (rs != null) {
				while(rs.next()){
          ResultSetMetaData rsmd = rs.getMetaData();
          System.out.println("querying SELECT * FROM XXX");
          int columnsNumber = rsmd.getColumnCount();
          while (rs.next()) {
              for (int i = 1; i <= columnsNumber; i++) {
                  if (i > 1) System.out.print(",  ");
                  String columnValue = rs.getString(i);
                  System.out.print(columnValue + " " + rsmd.getColumnName(i));
              }
              System.out.println("");
          }
					// Ad ad = new Ad();
          // ad.setId(rs.getInt("id"));
          // ad.setName(rs.getString("name"));
          // ad.setDescription(rs.getString("name"));
          // ad.setPrice(rs.getInt("name"));
          // ad.setSold(rs.getBoolean("name"));
          // ad.setDate(rs.getString("name"));
          // ad.setImage(rs.getString("name"));
          // User user = new User(rs.getInt(userid, username, password, phone, email, name, admin);
          // ad.setUser(user);
          // Category category = new Category();
          // ad.setCategory(category);
          // Subcategory subcategory = new Subcategory();
          // ad.setSubcategory(subcategory);
          // State state = new State();
          // ad.setState(state);
          // City city = new City();
          // ad.setCity(city);
					// ads.add(ad);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (ApplicationException ae){
			throw ae;
		}

		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ads;
  }

  // public User add(User user) throws ApplicationException {
  //   ResultSet rs = null;
  //   PreparedStatement stmt = null;
  //   try {
  //     stmt = FactoryConnection.getInstance().getConn().prepareStatement(
  //       "INSERT INTO classified_ads.user (username, password, name, phone, email) VALUES (?, ?, ?, ?, ?) RETURNING id"
  //     );
  //     stmt.setString(1, user.getUsername());
  //     stmt.setString(2, user.getPassword());
  //     stmt.setString(3, user.getName());
  //     stmt.setString(4, user.getPhone());
  //     stmt.setString(5, user.getEmail());
  //     rs = stmt.executeQuery();
  //     if (rs != null && rs.next()) {
  //       user.setId(rs.getInt(1));
  //     } else {
  //       throw new ApplicationException(new Throwable(), "El usuario ya está en uso.??????");
  //     }
  //   } catch (SQLException e) {
  //     e.printStackTrace();
  //     throw new ApplicationException(e, "Hubo un error al intentar crear el usuario.");
  //   } catch (ApplicationException e) {
  //     e.printStackTrace();
  //   } finally {
  //     try {
  //       if (rs != null) rs.close();
  //       if (stmt != null) stmt.close();
  //       FactoryConnection.getInstance().releaseConn();
  //     } catch (ApplicationException e) {
  //       e.printStackTrace();
  //     } catch (SQLException e) {
  //       e.printStackTrace();
  //     }
  //   }
  //   return user;
  // }
}
