package service;

import Response.EventIDResponse;
import Response.PersonIDResponse;
import Service.EventIDService;
import Service.EventService;
import Service.PersonIDService;
import Service.PersonService;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Provider;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonIDServiceTest {
  private Database db;
  private Person bestPerson;
  private PersonDao rDao;
  private Authtoken bestToken;
  private AuthTokenDao tDao;
  private PersonIDService personIDSer;

  @BeforeEach
  public void setUp() throws DataAccessException {
    db=new Database();
    bestPerson=new Person("123qwe", "Galeuser", "Gale",
            "Weathers", "F", "Father-123", "Mother-123",
            "Spouse-123");
    bestToken= new Authtoken("Jack_123A", "Galeuser");

    Connection conn=db.getConnection();
    db.clear();
    rDao= new PersonDao(conn);
    tDao = new AuthTokenDao(conn);
    personIDSer = new PersonIDService();
  }

  @Test
  public void PersonIDtestPass() throws DataAccessException {
    rDao.clear();
    rDao.insert(bestPerson);
    tDao.insert(bestToken);
    db.closeConnection(true);
    PersonIDResponse result = personIDSer.personID("123qwe","Jack_123A"  );
    assertNotNull(result.getPersonID());
    assertNotNull(result.getAssociatedUsername());
    assertNotNull(result.getGender());
    assertNotNull(result.getFirstName());
    assertNotNull(result.getLastName());
  }
  @Test
  public void PersonIDtestFail() throws DataAccessException {
    rDao.insert(bestPerson);
    tDao.insert(bestToken);
    PersonIDResponse result = personIDSer.personID("Jack_123A","djkghisldfhg465");
    assertEquals("Error: Invalid authtoken", result.getMessage());
    db.closeConnection(false);
  }
}