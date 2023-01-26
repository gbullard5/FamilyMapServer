package service;

import Request.LoadRequest;
import Request.RegisterRequest;
import Response.LoadResponse;
import Response.RegisterResponse;
import Service.ClearService;
import Service.LoadService;
import Service.RegisterService;
import dao.DataAccessException;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
  @BeforeEach
  public void setUp() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();


  }
  @Test
  public void LoadTestPass() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();
    float eventlat =(float) 43.6167;
    float eventlon =(float) -115.8;

    User user1 = new User ("sheila", "parker", "Sheila@parker.com",
            "Sheila", "Parker","f", "Sheila_Parker");
    User user2 = new User ("patrick", "spencer", "patrick@spencer.com",
            "Patrick", "Spencer", "m", "PatrickSpencer");
    Person person1 = new Person("Sheila_Parker", "sheila","Sheila",
            "Parker", "f","Betty_White","Blaine_McGary","Davis_Hyer");
    Person person2 = new Person("Davis_Hyer","sheila","Davis",
            "Hyer","m",null, null,"Sheila_Parker");
    Event event1 = new Event ("1235", "shelia","Sheila_Parker",
            eventlat,eventlon,"United States", "Boise", "birth", 1990);
    Event event2 = new Event("1234", "shelia", "Sheila_Parker",
            eventlat,eventlon, "United States", "Boise", "marriage", 2010);
    Event event3 = new Event ("1236", "shelia","Davis_Hyer",
            eventlat,eventlon,"United States", "Boise", "marriage", 2010);
    Event event4 = new Event ("1237", "shelia", "Sheila_Parker",
            eventlat,eventlon, "United States", "Boise", "marriage", 2010);
    User[] users = new User[]{user1, user2};
    Person[] person = new Person[]{person1, person2};
    Event[] event = new Event[] {event1, event2, event3, event4};

    LoadRequest loadRequest = new LoadRequest(users,person,event);
    LoadService loadService = new LoadService();
    LoadResponse loadResponse = loadService.Load(loadRequest);

    assertEquals("Successfully added 2 users, 2 persons, and 4 events to the database.", loadResponse.getMessage());
  }

  @Test
  public void LoadTestFail() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();
    float eventlat =(float) 43.6167;
    float eventlon =(float) -115.8;

    User user1 = new User ("sheila", "parker", "Sheila@parker.com",
            "Sheila", "Parker","f", "Sheila_Parker");
    User user2 = new User ("patrick", "spencer", "patrick@spencer.com",
            "Patrick", "Spencer", "m", "PatrickSpencer");
    Person person1 = new Person("Sheila_Parker", "sheila","Sheila",
            "Parker", "f","Betty_White","Blaine_McGary","Davis_Hyer");
    Person person2 = new Person("Davis_Hyer","sheila","Davis",
            "Hyer","m",null, null,"Sheila_Parker");
    Event event1 = new Event ("1235", "shelia","Sheila_Parker",
            eventlat,eventlon,"United States", "Boise", "birth", 1990);
    Event event2 = new Event("1234", "shelia", "Sheila_Parker",
            eventlat,eventlon, "United States", "Boise", "marriage", 2010);
    Event event3 = new Event ("1236", "shelia","Davis_Hyer",
            eventlat,eventlon,"United States", "Boise", "marriage", 2010);
    Event event4 = new Event ("1235", "shelia", "Sheila_Parker",
            eventlat,eventlon, "United States", "Boise", "marriage", 2010);
    User[] users = new User[]{user1, user2};
    Person[] person = new Person[]{person1, person2};
    Event[] event = new Event[] {event1, event2, event3, event4};

    LoadRequest loadRequest = new LoadRequest(users,person,event);
    LoadService loadService = new LoadService();
    LoadResponse loadResponse = loadService.Load(loadRequest);

    assertEquals("Error: eventID taken", loadResponse.getMessage());
  }

}