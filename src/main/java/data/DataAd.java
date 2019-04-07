package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;
import com.uploadcare.api.Client;

import entity.*;
import util.ApplicationException;

public class DataAd implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataAd() {}

  public Ad getById(Integer adId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		ResultSet rs = null;
		Ad ad = new Ad();
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
        "WHERE classified_ads.ad.id=?"
      );
      stmt.setInt(1, adId);
      rs = stmt.executeQuery();
			if (rs != null && rs.next()){
        ad.setId(rs.getInt(1));
        ad.setName(rs.getString(2));
        ad.setDescription(rs.getString(3));
        ad.setPrice(rs.getInt(4));
        ad.setSold(rs.getBoolean(5));
        ad.setDate(rs.getString(6));
        ad.setImage(rs.getString(8));
        User user = new User(rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getBoolean(17));
        ad.setUser(user);
        Category category = new Category(rs.getInt(21), rs.getString(22));
        Subcategory subcategory = new Subcategory(rs.getInt(18), rs.getString(19), category);
        ad.setSubcategory(subcategory);
        State state = new State(rs.getInt(26), rs.getString(27));
        City city = new City(rs.getInt(23), rs.getString(24), state);
        ad.setCity(city);
      } else {
        throw new ApplicationException("No se encontró el aviso especificado.");
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

		return ad;
  }

  public ArrayList<Ad> getAllByUser(Integer userId) throws SQLException, ApplicationException {
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
					Ad ad = new Ad();
          ad.setId(rs.getInt(1));
          ad.setName(rs.getString(2));
          ad.setDescription(rs.getString(3));
          ad.setPrice(rs.getInt(4));
          ad.setSold(rs.getBoolean(5));
          ad.setDate(rs.getString(6));
          ad.setImage(rs.getString(8));
          User user = new User(rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getBoolean(17));
          ad.setUser(user);
          Category category = new Category(rs.getInt(21), rs.getString(22));
          Subcategory subcategory = new Subcategory(rs.getInt(18), rs.getString(19), category);
          ad.setSubcategory(subcategory);
          State state = new State(rs.getInt(26), rs.getString(27));
          City city = new City(rs.getInt(23), rs.getString(24), state);
          ad.setCity(city);
					ads.add(ad);
				}
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al consultar la base de datos.");
		}

		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
		}

		return ads;
  }

  public void delete(Integer adId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "DELETE FROM classified_ads.ad WHERE id=?"
      );
      stmt.setInt(1, adId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró el aviso a eliminar.");
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

  public void markAsSold(Integer adId, Integer userId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "UPDATE classified_ads.ad SET sold=1 WHERE id=? AND user_id=?"
      );
      stmt.setInt(1, adId);
      stmt.setInt(2, userId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró el aviso a marcar como vendido.");
      }
    } catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al consultar la base de datos.");
		}

		try {
			if (stmt != null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
		}
  }

  public Ad createAd(Ad ad) throws ApplicationException {
    ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "INSERT INTO classified_ads.ad (name, description, price, date, user_id, image, city_id, subcategory_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"
      );
      stmt.setString(1, ad.getName());
      stmt.setString(2, ad.getDescription());
      stmt.setInt(3, ad.getPrice());
      stmt.setString(4, ad.getDate());
      stmt.setString(5, ad.getUser().getId());
      stmt.setString(6, ad.getImage());
      stmt.setString(7, ad.getCity().getId());
      stmt.setString(8, ad.getSubcategory().getId());
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        ad.setId(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(e, "Hubo un error al intentar crear el aviso.");
    } catch (ApplicationException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ad;
  }

  public Ad updateAd(Ad ad) throws ApplicationException {
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "UPDATE classified_ads.ad SET name=?, description=?, price=?, date=?, user_id=?, image=?, city_id=?, subcategory_id=? WHERE ?"
      );
      stmt.setString(1, ad.getName());
      stmt.setString(2, ad.getDescription());
      stmt.setInt(3, ad.getPrice());
      stmt.setString(4, ad.getDate());
      stmt.setString(5, ad.getUser().getId());
      stmt.setString(6, ad.getImage());
      stmt.setString(7, ad.getCity().getId());
      stmt.setString(8, ad.getSubcategory().getId());
      stmt.setInt(9, ad.getId());
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró el aviso a editar.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(e, "Hubo un error al intentar editar el aviso.");
    } catch (ApplicationException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (ApplicationException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ad;
  }
}
