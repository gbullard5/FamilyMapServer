package Response;


import models.Event;
import models.Person;

import java.util.ArrayList;

/**Response class for Person*/
public class PersonResponse extends ParentResponse {
  private ArrayList<Person> data;

  public PersonResponse(boolean success, String message) {
    super(success, message);
  }
  public PersonResponse(ArrayList<Person> person, boolean success) {
    super(success, null);
    this.data = person;
  }

  public ArrayList<Person> getData() {
    return data;
  }

  public void setData(ArrayList<Person> data) {
    this.data=data;
  }
}
