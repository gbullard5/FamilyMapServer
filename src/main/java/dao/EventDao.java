package dao;

import models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Connects to Event database */
public class EventDao {
  private final Connection conn;

  public EventDao(Connection conn) {
    this.conn = conn;
  }

  /** inserts into Event database */
  public void insert(Event event) throws DataAccessException{
    if(find(event.getEventID()) != null){
      throw new DataAccessException("Event already in Database");
    }
    String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
            "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getAssociatedUsername());
      stmt.setString(3, event.getPersonID());
      stmt.setFloat(4, event.getLatitude());
      stmt.setFloat(5, event.getLongitude());
      stmt.setString(6, event.getCountry());
      stmt.setString(7, event.getCity());
      stmt.setString(8, event.getEventType());
      stmt.setInt(9, event.getYear());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an event into the database");
    }
  }

  /** find in Event database */
  public Event find(String eventID) throws DataAccessException {
    Event event;
    ResultSet rs;
    String sql = "SELECT * FROM Event WHERE EventID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, eventID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                rs.getInt("Year"));
        return event;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
  }

  public ArrayList<Event> findAll(String username) throws DataAccessException {
    Event event= null;
    ArrayList<Event> eventArray = new ArrayList<>();
    ResultSet rs;
    String sql = "SELECT * FROM Event WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                rs.getInt("Year"));
        eventArray.add(event);
      }
      if(event == null){
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an event in the database");
    }
    return eventArray;
  }
  public void clearIndividual(String username) throws DataAccessException {
    String sql = "DELETE FROM Event WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();
    }catch(SQLException d){
      throw new DataAccessException("Error encountered while clearing the event line");
    }
  }

  /** clears Event database */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Event";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the event table");
    }
  }

}
