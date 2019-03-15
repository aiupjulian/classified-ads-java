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
			if (rs != null){
				while(rs.next()){
					State state = new State();
					state.setId(rs.getInt("id"));
					state.setName(rs.getString("name"));
          PreparedStatement substmt = FactoryConnection.getInstance().getConn().prepareStatement(
            "SELECT * FROM classified_ads.cities WHERE state_id=?"
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
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			FactoryConnection.getInstance().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return states;
	}
}