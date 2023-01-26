package service;

import Service.ClearService;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  private Database db;
  private Person bestPerson;
  private PersonDao pDao;
  private Authtoken bestToken;
  private AuthTokenDao tDao;
  private Event bestEvent;
  private EventDao eDao;
  private User bestUser;
  private UserDao uDao;
  private ClearService clearSer;

  @BeforeEach
  public void setUp() throws DataAccessException {
    db=new Database();
    bestPerson=new Person("Jack_123A", "Galeuser", "Gale",
            "Weathers", "F", "Father-123", "Mother-123",
            "Spouse-123");
    bestToken= new Authtoken("Jack_123A", "Galeuser");
    bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    bestUser= new User("theuser", "pass5", "email@email.com", "Steve", "Scott",
            "m", "ba53j-3d");

    Connection conn=db.getConnection();
    db.clear();
    pDao=new PersonDao(conn);
    tDao = new AuthTokenDao(conn);
    eDao = new EventDao(conn);
    uDao= new UserDao(conn);
    clearSer = new ClearService();
  }
  @AfterEach
  public void tearDown() {
    db.closeConnection(false);
  }
  @Test
  public void clearTest() throws DataAccessException{
    eDao.insert(bestEvent);
    tDao.insert(bestToken);
    uDao.insert(bestUser);
    pDao.insert(bestPerson);
    db.closeConnection(true);
    clearSer.clear();
    Connection conn=db.getConnection();
    pDao=new PersonDao(conn);
    tDao = new AuthTokenDao(conn);
    eDao = new EventDao(conn);
    uDao= new UserDao(conn);
    assertNull(eDao.find(bestEvent.getEventID()));
    assertNull(pDao.find(bestPerson.getPersonID()));
    assertNull(uDao.find(bestUser.getUsername()));
    assertNull(tDao.find(bestToken.getAuthtoken()));
  }
  @Test
  public void clearTestEmpty() throws DataAccessException{
    clearSer.clear();
    assertNull(eDao.find(bestEvent.getEventID()));
    assertNull(pDao.find(bestPerson.getPersonID()));
    assertNull(uDao.find(bestUser.getUsername()));
    assertNull(tDao.find(bestToken.getAuthtoken()));
  }
}