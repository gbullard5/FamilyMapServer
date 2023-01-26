package Service;

import Response.EventResponse;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import models.Authtoken;
import models.Event;

import java.sql.Connection;
import java.util.ArrayList;

/**Result class for Event*/
public class EventService {
  public EventService(){}
  /**Function returns EventResponse*/
  public EventResponse event(String authtoken){
    try{
      Database data = new Database();
      Connection conn = data.getConnection();
      AuthTokenDao auth = new AuthTokenDao(conn);
      Authtoken checkToken = auth.find(authtoken);
      data.closeConnection(true);
      if (checkToken == null){
        return new EventResponse(false,"Error: Invalid authtoken error", null);
      }
      else{
        Database dataEvent = new Database();
        Connection conn2 = dataEvent.getConnection();
        EventDao auth2 = new EventDao(conn2);
        ArrayList<Event> found = auth2.findAll(checkToken.getUsername()); //found needs to be an array
        dataEvent.closeConnection(true);
        EventResponse result = new EventResponse(true, "success", found);
        return result;
      }
    }catch(DataAccessException d){
      d.printStackTrace();
      return new EventResponse(false, "Error: Internal server error", null);
    }catch(Exception e){
      e.printStackTrace();
      return new EventResponse(false, "Error: Internal server error", null);
    }
  }
}
