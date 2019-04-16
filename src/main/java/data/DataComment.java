package data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.io.Serializable;
import java.sql.*;

import entity.*;
import util.*;

// java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
// ad.setDate(date.toString());

public class DataComment implements Serializable {
  private static final long serialVersionUID = 1L;

  public DataComment() {}

  public ArrayList<Comment> getAllByAdId(Integer adId) throws SQLException, ApplicationException {
    PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Comment> comments = new ArrayList<Comment>();
		try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "SELECT classified_ads.comment.*, classified_ads.user.name AS user_name, classified_ads.user.id AS user_id"
        + " FROM classified_ads.comment"
        + " INNER JOIN classified_ads.user ON classified_ads.comment.user_id=classified_ads.user.id"
        + " WHERE classified_ads.comment.ad_id=?"
      );
      stmt.setInt(1, adId);
      rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
          Comment comment = new Comment();
          comment.setId(rs.getInt("id"));
          User user = new User();
          user.setId(rs.getInt("user_id"));
          user.setName(rs.getString("user_name"));
          comment.setUser(user);
          comment.setText(rs.getString("text"));
          comment.setDate(rs.getString("date"));
          comments.add(comment);
        }
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

		return comments;
  }

  public Ad createComment(Comment comment) throws ApplicationException {
    ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
      stmt = FactoryConnection.getInstance().getConn().prepareStatement(
        "INSERT INTO classified_ads.comment (ad_id, user_id, text, date) VALUES (?, ?, ?, ?) RETURNING id"
      );
      java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
      comment.setDate(date.toString());
      stmt.setInt(1, comment.getPrice());
      stmt.setInt(2, comment.getPrice());
      stmt.setString(3, comment.getName());
      stmt.setDate(4, date);
      rs = stmt.executeQuery();
      if (rs != null && rs.next()) {
        comment.setId(rs.getInt(1));
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
    return comment;
  }
}