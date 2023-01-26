package models;

import java.util.Objects;

/** Model class for User*/
public class User {
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;

  /** Empty constructor for User*/
  public User(){
    this.username = null;
    this.password  = null;
    this.email  = null;
    this.firstName = null;
    this.lastName = null;
    this.gender = null;
    this.personID = null;
  }

  /** Constructor with parameters for User*/
  public User(String usernameInput, String passwordInput,String emailInput,String firstNameInput,
              String lastNameInput,String genderInput,String personIDInput) {
    this.username = usernameInput;
    this.password = passwordInput;
    this.email = emailInput;
    this.firstName = firstNameInput;
    this.lastName = lastNameInput;
    this.gender = genderInput;
    this.personID = personIDInput;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email=email;
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

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

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
    User e = (User) o;
    return Objects.equals(e.username, username) && Objects.equals(e.password, password) && Objects.equals(e.email, email)
            && Objects.equals(e.firstName, firstName) && Objects.equals(e.lastName, lastName) && Objects.equals(e.gender, gender) &&
            Objects.equals(e.gender, gender) && Objects.equals(e.personID, personID);
  }
}
