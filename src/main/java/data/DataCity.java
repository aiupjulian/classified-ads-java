package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.ApplicationException;

public class DataCity implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataCity() {}

  public ArrayList<City> getAll() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<City> cities = new ArrayList<City>();
		try {
      stmt = FactoryConnection.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(
				"SELECT city.id AS city_id, city.name AS city_name, state.id AS state_id, state.name AS state_name"
				+" FROM classified_ads.city"
				+" INNER JOIN classified_ads.state ON classified_ads.city.state_id=classified_ads.state.id"
			);
			if (rs != null) {
				while (rs.next()) {
					State state = new State();
					state.setId(rs.getInt("state_id"));
					state.setName(rs.getString("state_name"));
					City city = new City();
					city.setId(rs.getInt("city_id"));
					city.setName(rs.getString("city_name"));
					city.setState(state);
					cities.add(city);
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

		return cities;
	}

	public City getById(Integer cityId) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		City city = new City();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"SELECT city.id AS city_id, city.name AS city_name, state.id AS state_id, state.name AS state_name"
				+" FROM classified_ads.city"
				+" INNER JOIN classified_ads.state ON classified_ads.city.state_id=classified_ads.state.id"
				+" WHERE city.id=?"
			);
      stmt.setInt(1, cityId);
      rs = stmt.executeQuery();
			if (rs != null && rs.next()){
				State state = new State();
				state.setId(rs.getInt("state_id"));
				state.setName(rs.getString("state_name"));
				city.setId(rs.getInt("city_id"));
				city.setName(rs.getString("city_name"));
				city.setState(state);
      } else {
        throw new ApplicationException("No se encontró la ciudad especificada.");
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

		return city;
	}

	public void create(City city) throws SQLException, ApplicationException {
		ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"INSERT INTO classified_ads.city (name, state_id) VALUES (?, ?)"
			);
      stmt.setString(1, city.getName());
      stmt.setInt(2, city.getState().getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar crear la ciudad.");
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

	public void update(City city) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
				"UPDATE classified_ads.city SET name=?, state_id=? WHERE id=?"
			);
			stmt.setString(1, city.getName());
			stmt.setInt(2, city.getState().getId());
			stmt.setInt(3, city.getId());
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la ciudad a editar.");
      }
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar editar la ciudad.");
    } finally {
      try {
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
				throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
			}
    }
	}

	public void delete(Integer cityId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("DELETE FROM classified_ads.city WHERE id=?");
      stmt.setInt(1, cityId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la ciudad a eliminar.");
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