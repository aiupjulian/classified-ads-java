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
		PreparedStatement substmt = null;
		ResultSet subrs = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
      stmt = FactoryConnection.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM classified_ads.category");
			if (rs != null){
				while(rs.next()){
					Category category = new Category();
					category.setId(rs.getInt("id"));
					category.setName(rs.getString("name"));
          substmt = FactoryConnection.getInstance().getConn().prepareStatement(
            "SELECT * FROM classified_ads.subcategory WHERE category_id=?"
          );
          substmt.setInt(1, rs.getInt("id"));
          subrs = substmt.executeQuery();
          if (subrs != null) {
            ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
            while (subrs.next()) {
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
			if (subrs!=null) rs.close();
			if (substmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}

	public Category getById(Integer categoryId) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Category category = new Category();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("SELECT * FROM classified_ads.category WHERE id=?");
      stmt.setInt(1, categoryId);
      rs = stmt.executeQuery();
			if (rs != null && rs.next()){
				category.setId(categoryId);
        category.setName(rs.getString("name"));
      } else {
        throw new ApplicationException("No se encontró la categoría especificada.");
      }
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al consultar la base de datos.");
		}

		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
		}

		return category;
	}

	public void create(Category category) throws SQLException, ApplicationException {
		ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("INSERT INTO classified_ads.category (name) VALUES (?)");
      stmt.setString(1, category.getName());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar crear la categoría.");
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
				throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
			}
    }
	}

	public void update(Category category) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("UPDATE classified_ads.category SET name=? WHERE id=?");
			stmt.setString(1, category.getName());
			stmt.setInt(2, category.getId());
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la categoría a editar.");
      }
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar editar la categoría.");
    } finally {
      try {
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
				throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
			}
    }
	}

	public void delete(Integer categoryId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("DELETE FROM classified_ads.category WHERE id=?");
      stmt.setInt(1, categoryId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la categoría a eliminar.");
      }
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al consultar la base de datos.");
		}

		try {
			if (stmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
		}
  }
}