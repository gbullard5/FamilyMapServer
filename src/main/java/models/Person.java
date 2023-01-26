package models;

import java.util.Objects;

/** Model class for Person*/
public class Person {
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  /** Empty constructor for Person*/
  public Person(){
    this.personID = null;
    this.associatedUsername = null;
    this.firstName = null;
    this.lastName = null;
    this.gender = null;
    this.fatherID = null;
    this.motherID = null;
    this.spouseID = null;
  }

  /** Constructor with parameters for Person*/
  public Person(String personIDInput, String associatedUsernameInput,String firstNameInput, String lastNameInput,
                String genderInput,String fatherIDInput, String motherIDInput, String spouseIDInput){
    this.personID= personIDInput;
    this.associatedUsername = associatedUsernameInput;
    this.firstName = firstNameInput;
    this. lastName = lastNameInput;
    this.gender = genderInput;
    this.fatherID = fatherIDInput;
    this.motherID = motherIDInput;
    this.spouseID = spouseIDInput;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID=fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID=motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID=spouseID;
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
    Person p = (Person) o;
    return Objects.equals(p.personID, personID) && Objects.equals(p.associatedUsername, associatedUsername) && Objects.equals(p.firstName, firstName)
            && Objects.equals(p.lastName, lastName) && Objects.equals(p.gender, gender) && Objects.equals(p.fatherID, fatherID) &&
            Objects.equals(p.motherID, motherID) && Objects.equals(p.spouseID, spouseID);
  }
}
