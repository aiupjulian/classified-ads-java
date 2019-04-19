package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.ApplicationException;

public class DataSubcategory implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataSubcategory() {}

  public ArrayList<Subcategory> getAll() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
		try {
      stmt = FactoryConnection.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(
				"SELECT subcategory.id AS subcategory_id, subcategory.name AS subcategory_name, category.id AS category_id, category.name AS category_name"
				+" FROM classified_ads.subcategory"
				+" INNER JOIN classified_ads.category ON classified_ads.subcategory.category_id=classified_ads.category.id"
			);
			if (rs != null) {
				while (rs.next()) {
					Category category = new Category();
					category.setId(rs.getInt("category_id"));
					category.setName(rs.getString("category_name"));
					Subcategory subcategory = new Subcategory();
					subcategory.setId(rs.getInt("subcategory_id"));
					subcategory.setName(rs.getString("subcategory_name"));
					subcategory.setCategory(category);
					subcategories.add(subcategory);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (ApplicationException ae){
			throw ae;
		}

		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return subcategories;
	}

	public Subcategory getById(Integer subcategoryId) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Subcategory subcategory = new Subcategory();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"SELECT subcategory.id AS subcategory_id, subcategory.name AS subcategory_name, category.id AS category_id, category.name AS category_name"
				+" FROM classified_ads.subcategory"
				+" INNER JOIN classified_ads.category ON classified_ads.subcategory.category_id=classified_ads.category.id"
				+" WHERE subcategory.id=?"
			);
      stmt.setInt(1, subcategoryId);
      rs = stmt.executeQuery();
			if (rs != null && rs.next()){
				Category category = new Category();
				category.setId(rs.getInt("category_id"));
				category.setName(rs.getString("category_name"));
				subcategory.setId(rs.getInt("subcategory_id"));
				subcategory.setName(rs.getString("subcategory_name"));
				subcategory.setCategory(category);
      } else {
        throw new ApplicationException("No se encontró la subcategoría especificada.");
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

		return subcategory;
	}

	public void create(Subcategory subcategory) throws SQLException, ApplicationException {
		ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"INSERT INTO classified_ads.subcategory (name, category_id) VALUES (?, ?)"
			);
      stmt.setString(1, subcategory.getName());
      stmt.setInt(2, subcategory.getCategory().getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar crear la subcategoría.");
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

	public void update(Subcategory subcategory) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"UPDATE classified_ads.subcategory SET name=?, category_id=? WHERE id=?"
			);
			stmt.setString(1, subcategory.getName());
			stmt.setInt(2, subcategory.getCategory().getId());
			stmt.setInt(3, subcategory.getId());
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la subcategoría a editar.");
      }
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar editar la subcategoría.");
    } finally {
      try {
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
				throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
			}
    }
	}

	public void delete(Integer subcategoryId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("DELETE FROM classified_ads.subcategory WHERE id=?");
      stmt.setInt(1, subcategoryId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la subcategoría a eliminar.");
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