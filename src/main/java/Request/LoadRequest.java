package Request;

import models.Event;
import models.Person;
import models.User;

/** Request class for Load*/
public class LoadRequest {
  private User[] users;
  private Person[] persons;
  private Event[] events;

  /** Constructor for Load*/
  public LoadRequest(User[] usersInput, Person[] personsInput, Event[] eventsInput){
    this.users = usersInput;
    this.persons = personsInput;
    this.events = eventsInput;
  }

  public User[] getUsers() {
    return users;
  }

  public void setUsers(User[] users) {
    this.users=users;
  }

  public Person[] getPersons() {
    return persons;
  }

  public void setPersons(Person[] persons) {
    this.persons=persons;
  }

  public Event[] getEvents() {
    return events;
  }

  public void setEvents(Event[] events) {
    this.events=events;
  }
}
