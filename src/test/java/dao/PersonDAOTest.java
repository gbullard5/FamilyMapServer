package dao;

import models.Event;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
  private Database db;
  private Person bestPerson;
  private Person bestPerson2;
  private Person bestPerson3;
  private PersonDao pDao;

  @BeforeEach
  public void setUp() throws DataAccessException {
    // Here we can set up any classes or variables we will need for each test
    // lets create a new instance of the Database class
    db = new Database();
    // and a new event with random data
    bestPerson= new Person("Jack_123A", "Gale", "Gale",
            "Weathers", "F", "Father-123", "Mother-123",
            "Spouse-123");
    bestPerson2= new Person("Jack_123B", "Gale", "Gale",
            "Weathers", "F", "Father-123a", "Mother-123b",
            "Spouse-123b");
    bestPerson3= new Person("Jack_123C", "Gale2", "Gale",
            "Weathers", "F", "Father-123c", "Mother-123d",
            "Spouse-123a");

    // Here, we'll open the connection in preparation for the test case to use it
    Connection conn = db.getConnection();
    db.clear();
    //Then we pass that connection to the EventDAO, so it can access the database.
    pDao = new PersonDao(conn);
    //Let's clear the database as well so any lingering data doesn't affect our tests

  }

  @AfterEach
  public void tearDown() {
    // Here we close the connection to the database file, so it can be opened again later.
    // We will set commit to false because we do not want to save the changes to the database
    // between test cases.
    db.closeConnection(false);
  }

  @Test
  public void insertPass() throws DataAccessException {
    // Start by inserting an event into the database.
    pDao.clear();
    pDao.insert(bestPerson);
    // Let's use a find method to get the event that we just put in back out.
    Person compareTest = pDao.find(bestPerson.getPersonID());
    // First lets see if our find method found anything at all. If it did then we know that we got
    // something back from our database.
    assertNotNull(compareTest);
    // Now lets make sure that what we put in is the same as what we got out. If this
    // passes then we know that our insert did put something in, and that it didn't change the
    // data in any way.
    // This assertion works by calling the equals method in the Event class.
    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void insertFail() throws DataAccessException {
    // Let's do this test again, but this time lets try to make it fail.
    // If we call the method the first time the event will be inserted successfully.
    pDao.insert(bestPerson);

    // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
    // the same event again will cause the insert method to throw an exception, and we can verify this
    // behavior by using the assertThrows assertion as shown below.

    // Note: This call uses a lambda function. A lambda function runs the code that comes after
    // the "()->", and the assertThrows assertion expects the code that ran to throw an
    // instance of the class in the first parameter, which in this case is a DataAccessException.
    assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
  }

  @Test
  public void findPass() throws DataAccessException {

    pDao.clear();
    pDao.insert(bestPerson);

    Person compareTest = pDao.find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void findFail() throws DataAccessException {
    assertNull(pDao.find(bestPerson.getPersonID()));
  }

  @Test
  public void findAllPass() throws DataAccessException {
    pDao.clear();
    ArrayList<Person> person = new ArrayList<>();
    person.add(bestPerson);
    person.add(bestPerson2);
    try {
      pDao.insert(bestPerson);
      pDao.insert(bestPerson2);
      pDao.insert(bestPerson3);

      ArrayList<Person> person1 = pDao.findAll("Gale");
      for(int i = 0; i < person.size(); i++){
        assertEquals(person.get(i), person1.get(i));
      }
    }catch(DataAccessException d){
      d.printStackTrace();
    }

  }
  @Test
  public void findAllFail() throws DataAccessException {
    pDao.clear();
    ArrayList<Person> persons = new ArrayList<>();
    persons.add(bestPerson);
    persons.add(bestPerson2);
    try {
      pDao.insert(bestPerson);
      pDao.insert(bestPerson2);
      pDao.insert(bestPerson3);

      assertEquals(null, pDao.findAll("Gale3"));

    }catch(DataAccessException d){
      d.printStackTrace();
    }

  }

  @Test
  public void clearTest() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.clear();
    assertNull(pDao.find(bestPerson.getPersonID()));

  }
  @Test
  public void clearIndTest() throws DataAccessException {
    pDao.clear();
    pDao.insert(bestPerson);
    pDao.insert(bestPerson2);
    pDao.insert(bestPerson3);
    pDao.clearIndividual(bestPerson.getAssociatedUsername());
    ArrayList<Person> events1 = pDao.findAll("Gale2");
    assertEquals(events1.size(), 1);
  }

  @Test
  public void clearIndfail() throws DataAccessException {
    pDao.clear();
    pDao.insert(bestPerson);
    pDao.insert(bestPerson2);
    pDao.insert(bestPerson3);
    pDao.clearIndividual(bestPerson.getAssociatedUsername());
    ArrayList<Person> person1 = pDao.findAll("Gale2");
    assertNotEquals(person1.size(), 2);
  }
}
