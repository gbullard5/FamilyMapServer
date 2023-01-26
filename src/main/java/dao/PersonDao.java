package dao;

import models.Event;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Connects to Person database */
public class PersonDao {
  private final Connection conn;

  public PersonDao(Connection conn) {
    this.conn = conn;
  }

  /** inserts into Person database */
  public void insert(Person p) throws DataAccessException {
    if(find(p.getPersonID()) != null){
      throw new DataAccessException("Person already in Database");
    }
    String sql = "INSERT INTO Person (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
            "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, p.getPersonID());
      stmt.setString(2, p.getAssociatedUsername());
      stmt.setString(3, p.getFirstName());
      stmt.setString(4, p.getLastName());
      stmt.setString(5, p.getGender());
      stmt.setString(6, p.getFatherID());
      stmt.setString(7, p.getMotherID());
      stmt.setString(8, p.getSpouseID());


      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an event into the database");
    }
  }

  /** find in Person database */
  public Person find(String p) throws DataAccessException {
    Person person;
    ResultSet rs;
    String sql = "SELECT * FROM Person WHERE PersonID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, p);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Gender"),
                rs.getString("FatherID"),rs.getString("MotherID"), rs.getString("SpouseID"));
        return person;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
  }

  public ArrayList<Person> findAll(String username) throws DataAccessException {
    Person person= null;
    ArrayList<Person> personArray = new ArrayList<>();
    ResultSet rs;
    String sql = "SELECT * FROM Person WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Gender"),
                rs.getString("FatherID"),rs.getString("MotherID"), rs.getString("SpouseID"));
        personArray.add(person);
      }
      if(person == null){
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
    return personArray;
  }

  public void clearIndividual(String username) throws DataAccessException {
    String sql = "DELETE FROM Person WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();
  }catch(SQLException d){
      throw new DataAccessException("Error encountered while clearing the event line");
    }
    }

  /** clears Person database */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Person";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the event table");
    }
  }
  }

