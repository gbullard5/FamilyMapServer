package Response;

import models.Event;
import models.Person;
import models.User;

/**Response class for LoadRequest*/
public class LoadResponse extends ParentResponse {
  User[] users;
  Person[] persons;
  Event[] events;

  public LoadResponse(boolean success, String message) {
    super(success, message);
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
