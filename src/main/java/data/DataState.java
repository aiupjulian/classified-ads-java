package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.ApplicationException;

public class DataState implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataState() {}

  public ArrayList<State> getAllWithCities() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<State> states = new ArrayList<State>();
		try {
      stmt = FactoryConnection.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM classified_ads.state");
			if (rs != null) {
				while (rs.next()) {
					State state = new State();
					state.setId(rs.getInt("id"));
					state.setName(rs.getString("name"));
          PreparedStatement substmt = FactoryConnection.getInstance().getConn().prepareStatement(
            "SELECT * FROM classified_ads.city WHERE state_id=?"
          );
          substmt.setInt(1, rs.getInt("id"));
          ResultSet subrs = substmt.executeQuery();
          if (subrs != null) {
            ArrayList<City> cities = new ArrayList<City>();
            while (subrs.next()) {
              City city = new City();
              city.setId(subrs.getInt("id"));
              city.setName(subrs.getString("name"));
              cities.add(city);
            }
            state.setCities(cities);
          }
					states.add(state);
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

		return states;
	}

	public State getById(Integer stateId) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		State state = new State();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("SELECT * FROM classified_ads.state WHERE id=?");
      stmt.setInt(1, stateId);
      rs = stmt.executeQuery();
			if (rs != null && rs.next()){
				state.setId(stateId);
        state.setName(rs.getString("name"));
      } else {
        throw new ApplicationException("No se encontró la provincia especificada.");
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

		return state;
	}

	public void create(State state) throws SQLException, ApplicationException {
		ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("INSERT INTO classified_ads.state (name) VALUES (?)");
      stmt.setString(1, state.getName());
      rs = stmt.executeQuery();
    } catch (SQLException e) {
      throw new ApplicationException(e, "Hubo un error al intentar crear la provincia.");
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

	public void update(State state) throws SQLException, ApplicationException {
		PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("UPDATE classified_ads.state SET name=? WHERE ?");
      stmt.setString(1, ad.getName());
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la provincia a editar.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(e, "Hubo un error al intentar editar el aviso.");
    } finally {
      try {
        if (stmt != null) stmt.close();
        FactoryConnection.getInstance().releaseConn();
      } catch (SQLException e) {
				throw new ApplicationException(e, "Ocurrió un error al cerrar la conexión con la base de datos.");
			}
    }
	}

	public void delete(Integer stateId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement("DELETE FROM classified_ads.state WHERE id=?");
      stmt.setInt(1, stateId);
      Boolean success = stmt.executeUpdate() != 0;
      if (!success) {
        throw new ApplicationException("No se encontró la provincia a eliminar.");
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