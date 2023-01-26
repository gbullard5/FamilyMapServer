package Generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Event;
import models.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class EventGen {
  private String username;
  private Random random = new Random();
  private ArrayList<Event> all;

  public ArrayList<Event> getAll(){
    return all;
  }
  public EventGen(String user)
  {
    username = user;
    all = new ArrayList<Event>();
  }
  public void marriageEvent (Person man, Person woman, int year){
    int marriageYear = year + random.nextInt(6) + 22;
    Event marriage = genLocation();
    marriage.setEventType("Marriage");
    marriage.setYear(marriageYear);
    marriage.setPersonID(man.getPersonID());
    marriage.setEventID(UUID.randomUUID().toString());
    marriage.setAssociatedUsername(man.getAssociatedUsername());
    Event marriagecopy = new Event();
    marriagecopy.setYear(marriageYear);
    marriagecopy.setEventType("Marriage");
    marriagecopy.setPersonID(woman.getPersonID());
    marriagecopy.setLatitude(marriage.getLatitude());
    marriagecopy.setLongitude(marriage.getLongitude());
    marriagecopy.setCity(marriage.getCity());
    marriagecopy.setCountry(marriage.getCountry());
    marriagecopy.setEventID(UUID.randomUUID().toString());
    marriagecopy.setAssociatedUsername(marriage.getAssociatedUsername());
    all.add(marriage);
    all.add(marriagecopy);
  }
public void deathEvent(Person person, int year){
    Event death = genLocation();
    int avgLife = 40;
    int deathYear = year + avgLife + random.nextInt(60);
    if(deathYear > 2022){
      deathYear = 2022;
    }
    death.setAssociatedUsername(person.getAssociatedUsername());
    death.setEventType("death");
    death.setEventID(UUID.randomUUID().toString());
    death.setPersonID(person.getPersonID());
    death.setYear(deathYear);
    all.add(death);

}

  public void birthEvent(Person person, int year){
    Event birth = genLocation();
    int birthYear = year- random.nextInt(5);
    birth.setAssociatedUsername(person.getAssociatedUsername());
    birth.setEventType("birth");
    birth.setEventID(UUID.randomUUID().toString());
    birth.setYear(birthYear);
    birth.setPersonID(person.getPersonID());
    all.add(birth);
  }

  public Event genLocation(){
    Random rand = new Random();
    Event eventLoc = new Event();
    try{
      FileReader fileReader = new FileReader("json/locations.json");
      JsonParser jsonParser = new JsonParser();
      JsonObject obj = (JsonObject) jsonParser.parse(fileReader);
      JsonArray locationArray = (JsonArray) obj.get("data");
      int pos = rand.nextInt(locationArray.size());
      JsonObject curLoc = (JsonObject) locationArray.get(pos);
      String country = curLoc.get("country").toString().substring(1,curLoc.get("country").toString().length()-1);
      String city = curLoc.get("city").toString().substring(1,curLoc.get("city").toString().length()-1);

      eventLoc.setCountry(country);
      eventLoc.setCity(city);
      eventLoc.setLatitude(curLoc.get("latitude").getAsLong());
      eventLoc.setLongitude(curLoc.get("longitude").getAsLong());
      return eventLoc;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

}
