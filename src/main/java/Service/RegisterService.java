package Service;

import Generator.FamilyGenerator;
import Generator.GenData;
import Request.RegisterRequest;
import Response.RegisterResponse;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;
import models.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**Result class for Register*/
public class RegisterService {
  Authtoken authtoken = new Authtoken();
  User user = new User();
  Person person = new Person();
  FamilyGenerator familyGenerator = new FamilyGenerator();
  private int stdGen = 4;

  /**Function takes input and returns RegisterResponse*/
  public RegisterResponse Register(RegisterRequest r) throws DataAccessException {
    Database data = new Database();
    Connection conn = data.getConnection();
    AuthTokenDao aDao = new AuthTokenDao(conn);
    EventDao eDao = new EventDao(conn);
    PersonDao pDao = new PersonDao(conn);
    UserDao uDao = new UserDao(conn);

    if(r.getUsername() == null || r.getEmail() == null || r.getPassword() == null ||
            r.getFirstName() == null || r.getLastName() == null || r.getGender() == null){
      data.closeConnection(false);
      return new RegisterResponse("Error: Invalid input",false);
    }

    user.setUsername(r.getUsername());
    user.setEmail(r.getEmail());
    user.setPassword(r.getPassword());
    user.setGender(r.getGender());
    user.setFirstName(r.getFirstName());
    user.setLastName(r.getLastName());
    user.setPersonID(UUID.randomUUID().toString());
    person.setAssociatedUsername(user.getUsername());
    person.setFirstName(user.getFirstName());
    person.setLastName(user.getLastName());
    person.setGender(user.getGender());
    person.setPersonID(user.getPersonID());
    try{
      User test = uDao.find(user.getUsername());
      if(test == null){
        uDao.insert(user);
        authtoken = new Authtoken(user.getUsername());
        aDao.insert(authtoken);
        GenData familyData = familyGenerator.genGen(stdGen, person);


        ArrayList<Person> personArray = familyData.getPersonArray();
        for(int i = 0; i < personArray.size(); i++){
          pDao.insert(personArray.get(i));
        }

        ArrayList<Event> eventArray = familyData.getEventArray();
        for(int i = 0; i < eventArray.size(); i++){
          eDao.insert(eventArray.get(i));
        }
        RegisterResponse result = new RegisterResponse(authtoken.getAuthtoken(), user.getUsername(), user.getPersonID() ,true);
        data.closeConnection(true);
        return result;
      }else{
        data.closeConnection(false);
        return new RegisterResponse("Error: Username taken",false);
      }

    } catch (DataAccessException e) {
     return new RegisterResponse("Error: Internal server error", false );
    }
  }
}
