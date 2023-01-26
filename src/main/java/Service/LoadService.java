package Service;

import Request.LoadRequest;
import Response.ClearResponse;
import Response.LoadResponse;
import dao.*;
import models.Event;
import models.Person;
import models.User;

import java.sql.Connection;

/**Result class for Load*/
public class LoadService {

  /**Function takes input and returns LoadResponse*/
  public LoadResponse Load(LoadRequest r) throws DataAccessException {
    try {
      Database data=new Database();
      Connection conn=data.getConnection();
      AuthTokenDao aDao=new AuthTokenDao(conn);
      aDao.clear();
      UserDao uDao=new UserDao(conn);
      uDao.clear();
      User[] user=r.getUsers();
      int countU = 0;
      int countP = 0;
      int countE = 0;
      for (int i=0; i < user.length; i++) {
        if(user[i].getUsername() == null || user[i].getPassword() == null|| user[i].getEmail() == null|| user[i].getFirstName() == null||
        user[i].getLastName() == null|| user[i].getGender() == null || user[i].getPersonID() == null){
          data.closeConnection(false);
          return new LoadResponse(false, "Error: Invalid input");
        }
        if(uDao.find(user[i].getUsername()) != null){
          data.closeConnection(false);
          return new LoadResponse(false, "Error: username taken");
        }
        uDao.insert(user[i]);
        countU++;
      }
      PersonDao pDao=new PersonDao(conn);
      pDao.clear();
      Person[] person=r.getPersons();
      for (int i=0; i < person.length; i++) {
        if(person[i].getPersonID() == null ||person[i].getAssociatedUsername() == null ||person[i].getFirstName() == null ||person[i].getLastName() == null ||
                person[i].getGender() == null){
          data.closeConnection(false);
          return new LoadResponse(false, "Error: Invalid input");
        }
        if(pDao.find(person[i].getPersonID()) != null){
          data.closeConnection(false);
          return new LoadResponse(false, "Error: personID taken");
        }
        pDao.insert(person[i]);
        countP++;
      }
      EventDao eDao=new EventDao(conn);
      eDao.clear();
      Event[] event=r.getEvents();
      for (int i=0; i < event.length; i++) {
        if(event[i].getEventID() ==null||event[i].getAssociatedUsername()==null ||event[i].getEventType()==null ||
                event[i].getPersonID()==null ||event[i].getCity()==null ||event[i].getCountry()==null) {
          data.closeConnection(false);
          return new LoadResponse(false, "Error: Invalid input");
        }
        if(eDao.find(event[i].getEventID()) != null){
          data.closeConnection(false);
          return new LoadResponse(false, "Error: eventID taken");
        }
        eDao.insert(event[i]);
        countE++;
      }

      LoadResponse result=new LoadResponse(true, "Successfully added "+countU+ " users, "+ countP +" persons, and "+countE+ " events to the database.");
      data.closeConnection(true);
      return result;
    } catch (DataAccessException d) {
      d.printStackTrace();
      return new LoadResponse(false, "Error: Internal Server Error");

    }
  }
}
