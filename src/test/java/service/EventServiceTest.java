package service;

import Response.EventResponse;
import Service.ClearService;
import Service.EventService;
import dao.*;
import models.Authtoken;
import models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
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
  public void EventTestPass() throws DataAccessException {
    Event event1 = new Event("eventid1", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "birth", 2016);
    Event event2 = new Event("eventid2", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "marriage", 2016);
    Event event3 = new Event("eventid3", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "death", 2016);
    Event event4 = new Event("eventid4", "none", "Gale123d",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event event5 = new Event("eventid5", "none", "Gale123e",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Graduation", 2016);
    Connection conn=db.getConnection();
    EventDao eDao = new EventDao(conn);
    eDao.clear();
    eDao.insert(event1);
    eDao.insert(event2);
    eDao.insert(event3);
    eDao.insert(event4);
    eDao.insert(event5);
    db.closeConnection(true);

    EventService eventService = new EventService();
    EventResponse eventResponse= eventService.event("token1");

    ArrayList<Event> eventArrayList = new ArrayList<>();
    eventArrayList.add(event1);
    eventArrayList.add(event2);
    eventArrayList.add(event3);

    for(int i=0; i<eventResponse.getData().size(); i++){
      assertEquals(eventArrayList.get(i), eventResponse.getData().get(i));
    }
  }
  @Test
  public void EventTestFail() throws DataAccessException {
    Event event1=new Event("eventid1", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "birth", 2016);
    Event event2=new Event("eventid2", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "marriage", 2016);
    Event event3=new Event("eventid3", "username1", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "death", 2016);
    Event event4=new Event("eventid4", "none", "Gale123d",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event event5=new Event("eventid5", "none", "Gale123e",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Graduation", 2016);
    Connection conn=db.getConnection();
    EventDao eDao=new EventDao(conn);
    eDao.clear();
    eDao.insert(event1);
    eDao.insert(event2);
    eDao.insert(event3);
    eDao.insert(event4);
    eDao.insert(event5);
    db.closeConnection(true);

    EventService eventService=new EventService();
    EventResponse eventResponse=eventService.event("token7");
    assertEquals("Error: Invalid authtoken error", eventResponse.getMessage());
  }
}