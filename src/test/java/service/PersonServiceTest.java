package service;

import Response.PersonResponse;
import Service.ClearService;
import Service.PersonService;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
  Database db=new Database();

  @BeforeEach
  public void setup() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();
    Connection conn=db.getConnection();
    db.clear();
    AuthTokenDao aDao=new AuthTokenDao(conn);
    aDao.insert(new Authtoken( "token1","username1"));
    aDao.insert(new Authtoken("token2","username2"));
    db.closeConnection(true);
  }
  @Test
  public void PersonTestPass() throws DataAccessException {
    Person person1 = new Person("Jack_123A", "username1", "Gale",  "Weathers",
            "F", "Father-123", "Mother-123", "Spouse-123");
    Person person2 = new Person("Jack_123B", "username1", "Gales",  "Weather",
            "M", "Father-124", "Mother-124", "Spouse-123");
    Person person3 = new Person("Jack_123C", "username1", "Gal",  "Weath",
            "F",null,null,null);
    Person person4 =  new Person("Jack_123D", "none", "Gal",  "We",
            "M",null,null,null);
    Person person5 = new Person("Jack_123E", "none", "Gafdg",  "Wahf",
            "M",null,null,null);
    Connection conn=db.getConnection();
    PersonDao pDao = new PersonDao(conn);
    pDao.clear();
    pDao.insert(person1);
    pDao.insert(person2);
    pDao.insert(person3);
    pDao.insert(person4);
    pDao.insert(person5);
    db.closeConnection(true);

    PersonService personService = new PersonService();
    PersonResponse personResponse = personService.person("token1");

    ArrayList<Person> personArrayList = new ArrayList<>();
    personArrayList.add(person1);
    personArrayList.add(person2);
    personArrayList.add(person3);

    for(int i=0; i<personResponse.getData().size(); i++){
      assertEquals(personArrayList.get(i), personResponse.getData().get(i));
    }
  }
  @Test
  public void PersonTestFail() throws DataAccessException {
    Person person1 = new Person("Jack_123A", "username1", "Gale",  "Weathers",
            "F", "Father-123", "Mother-123", "Spouse-123");
    Person person2 = new Person("Jack_123B", "username1", "Gales",  "Weather",
            "M", "Father-124", "Mother-124", "Spouse-123");
    Person person3 = new Person("Jack_123C", "username1", "Gal",  "Weath",
            "F",null,null,null);
    Person person4 =  new Person("Jack_123D", "none", "Gal",  "We",
            "M",null,null,null);
    Person person5 = new Person("Jack_123E", "none", "Gafdg",  "Wahf",
            "M",null,null,null);
    Connection conn=db.getConnection();
    PersonDao pDao = new PersonDao(conn);
    pDao.clear();
    pDao.insert(person1);
    pDao.insert(person2);
    pDao.insert(person3);
    pDao.insert(person4);
    pDao.insert(person5);
    db.closeConnection(true);

    PersonService personService = new PersonService();
    PersonResponse personResponse = personService.person("token5");
    assertEquals("Error: Invalid auth token", personResponse.getMessage());
    }
  }
