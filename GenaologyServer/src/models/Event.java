package models;

import java.util.Objects;
import java.util.UUID;

/** Model class for Event*/
public class Event {
  private String eventID;
  private String associatedUsername;
  private String personID;
  private float latitude;
  private float longitude;
  private String country;
  private String city;
  private String eventType;
  private int year;

  /** Empty constructor for Event*/
  public Event(){
    this.eventID = null;
    this.associatedUsername = null;
    this.personID = null;
    this.latitude = 0;
    this.longitude = 0;
    this.country = null;
    this.city = null;
    this.eventType = null;
    this.year = 0;
  }

  /** Constructor with parameters for Event*/
  public Event(String eventIDInput, String associatedUsernameInput, String personIDInput, float latitudeInput,
               float longitudeInput, String countryInput, String cityInput, String eventTypeInput, int yearInput){
    this.eventID = eventIDInput;
    this.associatedUsername = associatedUsernameInput;
    this.personID = personIDInput;
    this.latitude = latitudeInput;
    this.longitude = longitudeInput;
    this.country = countryInput;
    this.city = cityInput;
    this.eventType = eventTypeInput;
    this.year = yearInput;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude=latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude=longitude;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country=country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city=city;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType=eventType;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year=year;
  }

  @Override
  public boolean equals(Object o){
    if (o == null){
      return false;
    }
    if (o.getClass() != this.getClass()){
      return false;
    }
    if(o == this){
      return true;
    }
    Event e = (Event) o;
    return Objects.equals(e.eventID, eventID) && Objects.equals(e.associatedUsername, associatedUsername) && Objects.equals(e.personID, personID)
            && Objects.equals(e.latitude, latitude) && Objects.equals(e.longitude, longitude) && Objects.equals(e.country, country) &&
            Objects.equals(e.city, city) && Objects.equals(e.eventType, eventType) && Objects.equals(e.year, year);
  }
}
