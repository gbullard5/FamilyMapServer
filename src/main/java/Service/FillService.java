package Service;

import Generator.FamilyGenerator;
import Generator.GenData;
import Response.FillResponse;
import dao.*;
import models.Event;
import models.Person;
import models.User;

import java.sql.Connection;
import java.util.ArrayList;

/**Result class for Fill*/
public class FillService {
  /**Function takes input and returns FillResponse*/
  public FillResponse fill(String username, int generations) throws DataAccessException {
    Database data = new Database();
    Connection conn = data.getConnection();
    EventDao eDao = new EventDao(conn);
    PersonDao pDao = new PersonDao(conn);
    UserDao uDao = new UserDao(conn);

    if(generations < 0){
      data.closeConnection(false);
      return new FillResponse(false, "Error: Invalid input");
    }
    try{
      FamilyGenerator gen = new FamilyGenerator();
      User user = uDao.find(username);

      if(user == null){
        data.closeConnection(false);
        return new FillResponse(false, "Error: Invalid username");
      }else{
        Person person = new Person();
        pDao.clearIndividual(username);
        eDao.clearIndividual(username);
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setGender(user.getGender());
        person.setPersonID(user.getPersonID());
        person.setAssociatedUsername(username);

        GenData familyData = gen.genGen(generations, person);
        int countP = 0;
        int countE = 0;
        ArrayList<Person> personArray = familyData.getPersonArray();
        for(int i = 0; i < personArray.size(); i++){
          pDao.insert(personArray.get(i));
          countP++;
        }

        ArrayList<Event> eventArray = familyData.getEventArray();
        for(int i = 0; i < eventArray.size(); i++){
          eDao.insert(eventArray.get(i));
          countE++;
        }
        data.closeConnection(true);
        return new FillResponse(true, "Successfully added "+ countP +" persons and "+countE+ " events to the database.");
      }

    } catch (DataAccessException e) {
      e.printStackTrace();
      return new FillResponse(false, "Error: Internal server error");
    }

  }
}
