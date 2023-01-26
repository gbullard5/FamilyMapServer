package Service;

import Response.EventIDResponse;
import Response.PersonIDResponse;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;

import java.sql.Connection;

/**Result class for PersonID*/
public class PersonIDService {
  /**
   * Function takes input and returns PersonIDResponse
   */
  public PersonIDResponse personID(String id, String authtoken) throws DataAccessException {
    try {
      Database data=new Database();
      Connection conn=data.getConnection();
      AuthTokenDao auth=new AuthTokenDao(conn);
      Authtoken checkToken=auth.find(authtoken);
      data.closeConnection(true);
      if (checkToken == null) {
        return new PersonIDResponse(false, "Error: Invalid authtoken");
      } else {
        Database dataPerson=new Database();
        Connection conn2=dataPerson.getConnection();
        PersonDao auth2=new PersonDao(conn2);
        Person found=auth2.find(id);

        if (!checkToken.getUsername().equals(found.getAssociatedUsername())) {
          dataPerson.closeConnection(false);
          return new PersonIDResponse(false, "Error: Requested person does not belong to this user");
        }
        if (found == null) {
          dataPerson.closeConnection(false);
          return new PersonIDResponse(false, "Error: Invalid personID");
        } else {
          PersonIDResponse result=new PersonIDResponse(found.getAssociatedUsername(), found.getPersonID(), found.getFirstName(), found.getLastName(),
                  found.getGender(), found.getFatherID(), found.getMotherID(), true);
          dataPerson.closeConnection(true);
          return result;
        }
      }
    } catch (
            DataAccessException d) {
      return new PersonIDResponse(false, "Error: Internal server error");
    }
  }
}

