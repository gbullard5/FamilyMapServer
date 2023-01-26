package Service;

import Response.EventIDResponse;
import Response.EventResponse;
import Response.PersonIDResponse;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import models.Authtoken;
import models.Event;

import java.sql.Connection;
import java.util.ArrayList;

/**Result class for EventID*/
public class EventIDService {
  /**Function takes input and returns EventIDResponse*/
  public EventIDResponse eventID(String id, String authtoken){
    try{
      Database data = new Database();
      Connection conn = data.getConnection();
      AuthTokenDao auth = new AuthTokenDao(conn);
      Authtoken checkToken = auth.find(authtoken);
      data.closeConnection(true);
      if (checkToken == null){
        return new EventIDResponse(false,"Error: Invalid authtoken");
      }
      else{
        Database dataEvent = new Database();
        Connection conn2 = dataEvent.getConnection();
        EventDao auth2 = new EventDao(conn2);
        Event found = auth2.find(id);
        if (!checkToken.getUsername().equals(found.getAssociatedUsername())) {
          dataEvent.closeConnection(false);
          return new EventIDResponse(false, "Error: Requested event does not belong to this user");
        }
        if(found == null){
          dataEvent.closeConnection(false);
          return new EventIDResponse(false,"Error: Invalid eventID");
        }else {
          dataEvent.closeConnection(true);
          EventIDResponse result=new EventIDResponse(found.getAssociatedUsername(), found.getEventID(), found.getPersonID(), found.getLatitude(), found.getLongitude(),
                  found.getCountry(), found.getCity(), found.getEventType(), found.getYear(), true);

          return result;
        }
      }
    }catch(DataAccessException d){
      return new EventIDResponse(false, "Error: Internal server error");
    }
  }

}
