/*package data;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

import entity.State;
import util.ApplicationException;

public class DataSubcategory implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataSubcategory() {}

  public ArrayList<State> getAll() throws Exception {
		Statement stmt=null;
		ResultSet rs=null;
		ArrayList<State> pers= new ArrayList<State>();
		try {
			stmt = FactoryConexion.getInstancia()
					.getConn().createStatement();
			rs = stmt.executeQuery("select * from persona");
			if(rs!=null){
				while(rs.next()){
					Persona p=new Persona();
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.setDni(rs.getString("dni"));
					p.setHabilitado(rs.getBoolean("habilitado"));
					pers.add(p);
				}
			}
		} catch (SQLException e) {
			
			throw e;
		} catch (ApplicationException ae){
			throw ae;
		}
		

		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pers;
		
	}


  // public State[] getAll() {
  //   State[] states = new State[]();
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
}*/