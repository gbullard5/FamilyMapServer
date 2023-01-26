package service;

import Response.EventIDResponse;
import Service.EventIDService;
import dao.*;
import models.Authtoken;
import models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

class EventIDServiceTest {
  private Database db;
  private Authtoken bestToken;
  private AuthTokenDao tDao;
  private Event bestEvent;
  private EventDao eDao;
  private EventIDService eventIDSer;

  @BeforeEach
  public void setUp() throws DataAccessException {
    db=new Database();
    bestEvent = new Event("fjghrusdfj", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    bestToken= new Authtoken("Jack_123A", "Gale");

    Connection conn=db.getConnection();
    db.clear();
    eDao = new EventDao(conn);
    tDao = new AuthTokenDao(conn);
    eventIDSer = new EventIDService();
  }
  @Test
  public void EventIDpassTest() throws DataAccessException {
    eDao.clear();
    eDao.insert(bestEvent);
    tDao.insert(bestToken);
    db.closeConnection(true);
    EventIDResponse result = eventIDSer.eventID("fjghrusdfj", "Jack_123A");
    assertNotNull(result.getCity());
    assertNotNull(result.getCountry());
    assertNotNull(result.getEventType());
    assertNotNull(result.getEventID());
    assertEquals(2016, result.getYear());
    assertEquals(35.9f, result.getLatitude());
    assertEquals(140.1f, result.getLongitude());
  }
  @Test
  public void EventIDtestFail() throws DataAccessException {
    eDao.insert(bestEvent);
    tDao.insert(bestToken);
    EventIDResponse result = eventIDSer.eventID("fjghrusdfj","djkghisldfhg465");
    assertEquals("Error: Invalid authtoken", result.getMessage());
    db.closeConnection(false);
  }

}