package dao;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Connects to User database */
public class UserDao {
  private final Connection conn;

  public UserDao(Connection conn) {
    this.conn = conn;
  }

  /** inserts into User database */
  public void insert(User u) throws DataAccessException {
    if(find(u.getUsername()) != null){
      throw new DataAccessException("Username already in Database");
    }
    if(find(u.getPersonID()) != null){
      throw new DataAccessException("PersonID already in Database");
    }
    String sql = "INSERT INTO User (Username, Password, Email, FirstName, Lastname, " +
            "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, u.getUsername());
      stmt.setString(2, u.getPassword());
      stmt.setString(3, u.getEmail());
      stmt.setString(4, u.getFirstName());
      stmt.setString(5, u.getLastName());
      stmt.setString(6, u.getGender());
      stmt.setString(7, u.getPersonID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting a user into the database");
    }
  }

  /** find in User database */
  public User find(String u) throws DataAccessException {
    User user;
    ResultSet rs;
    String sql = "SELECT * FROM User WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, u);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"),
                rs.getString("Email"), rs.getString("FirstName"),
                rs.getString("LastName"), rs.getString("Gender"),
                rs.getString("PersonID"));
        return user;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an user in the database");
    }
  }

  /** clears User database */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM User";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the user table");
    }

  }
}
