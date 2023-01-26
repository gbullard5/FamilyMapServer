package Service;

import Response.EventResponse;
import Response.PersonResponse;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;

import java.sql.Connection;
import java.util.ArrayList;

/**Result class for Person*/
public class PersonService {
  public PersonService(){}
  /**Function takes input and returns PersonResponse*/
  public PersonResponse person(String authtoken){
    try{
      Database data = new Database();
      Connection conn = data.getConnection();
      AuthTokenDao auth = new AuthTokenDao(conn);
      Authtoken checkToken = auth.find(authtoken);
      if (checkToken == null){
        data.closeConnection(false);
        return new PersonResponse(false,"Error: Invalid auth token");
      }
      else{
        Database dataPerson = new Database();
        Connection conn2 = dataPerson.getConnection();
        PersonDao auth2 = new PersonDao(conn2);
        ArrayList<Person> found = auth2.findAll(checkToken.getUsername()); //found needs to be an array
        PersonResponse result = new PersonResponse(found,true);
        dataPerson.closeConnection(true);
        data.closeConnection(true);
        return result;
      }
    }catch(DataAccessException d){
      return new PersonResponse(false, "Error: Internal server error");
    }
  }
}
