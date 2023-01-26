package dao;

import models.Authtoken;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Connects to Authtoken database */
public class AuthTokenDao {

  private final Connection conn;


  public AuthTokenDao(Connection conn) {
    this.conn = conn;
  }


  /** inserts into Authtoken database */
  public void insert(Authtoken p) throws DataAccessException {
    if(find(p.getAuthtoken()) != null){
      throw new DataAccessException("Token already in Database");
    }
    String sql = "INSERT INTO Authtoken (Authtoken, Username) VALUES(?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, p.getAuthtoken());
      stmt.setString(2, p.getUsername());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an event into the database");
    }
  }

  /** find in Authtoken database */
  public Authtoken find(String p) throws DataAccessException {
    Authtoken authtoken;
    ResultSet rs;
    String sql = "SELECT * FROM Authtoken WHERE Authtoken = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, p);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authtoken = new Authtoken(rs.getString("Authtoken"), rs.getString("Username"));
        return authtoken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an Authtoken in the database");
    }
  }

  /** clears Authtoken database */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Authtoken";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the Authtoken table");
    }
  }
}
