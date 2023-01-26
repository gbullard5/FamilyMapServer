package Generator;

import models.Event;
import models.Person;

import java.util.ArrayList;

public class GenData {
  private ArrayList<Event> eventArray;
  private ArrayList<Person> personArray;

  public GenData(ArrayList<Person> personArray, ArrayList<Event> eventArray){
    this.eventArray = eventArray;
    this.personArray = personArray;
  }


  public ArrayList<Event> getEventArray() {
    return eventArray;
  }

  public void setEventArray(ArrayList<Event> eventArray) {
    this.eventArray=eventArray;
  }

  public ArrayList<Person> getPersonArray() {
    return personArray;
  }

  public void setPersonArray(ArrayList<Person> personArray) {
    this.personArray=personArray;
  }
}
