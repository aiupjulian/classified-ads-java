package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.ApplicationException;

public class DataCategory implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataCategory() {}

  public ArrayList<Category> getAllWithSubcategories() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
      stmt = FactoryConnection.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM classified_ads.category");
			if (rs != null){
				while(rs.next()){
					Category category = new Category();
					category.setId(rs.getInt("id"));
					category.setName(rs.getString("name"));
          PreparedStatement substmt = FactoryConnection.getInstance().getConn().prepareStatement(
            "SELECT * FROM classified_ads.subcategory WHERE category_id=?"
          );
          substmt.setInt(1, rs.getInt("id"));
          ResultSet subrs = substmt.executeQuery();
          if (subrs != null) {
            ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
            while (rs.next()) {
              Subcategory subcategory = new Subcategory();
              subcategory.setId(subrs.getInt("id"));
              subcategory.setName(subrs.getString("name"));
              subcategories.add(subcategory);
            }
            category.setSubcategories(subcategories);
          }
					categories.add(category);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}


  // public Category[] getAll() {
  //   Category[] categories = new Category[]();
  //   PreparedStatement stmt = null;
  //   ResultSet rs = null;

  //   try {
  //     stmt = FactoryConnection.getInstance().getConn().prepareStatement(
  //       "SELECT * FROM user WHERE id=?"
  //     );
  //     stmt.setInt(1, id);
  //     rs = stmt.executeQuery();
  //     if (rs != null && rs.next()) {
  //       user = new User(
  //         id,
  //         rs.getString("username"),
  //         rs.getString("password"),
  //         rs.getString("phone"),
  //         rs.getString("email"),
  //         rs.getString("name"),
  //         rs.getBoolean("admin")
  //       );
  //     }
  //   } catch (SQLException e) {
  //     // TODO Auto-generated catch block
  //     e.printStackTrace();
  //   } catch (ApplicationException e) {
  //     // TODO Auto-generated catch block
  //     e.printStackTrace();
  //   } finally {
  //     try {
  //       if (rs != null)
  //         rs.close();
  //       if (stmt != null)
  //         stmt.close();
  //       FactoryConnection.getInstance().releaseConn();
  //     } catch (SQLException e) {
  //       // TODO Auto-generated catch block
  //       e.printStackTrace();
  //     } catch (ApplicationException e) {
  //       // TODO Auto-generated catch block
  //       e.printStackTrace();
  //     }
  //   }
  //   return user;
  // }
}