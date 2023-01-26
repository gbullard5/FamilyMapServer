package models;

import java.util.Objects;
import java.util.UUID;

/** Model class for Authtoken*/
public class Authtoken {
  private String authtoken;
  private String username;

  /** Empty constructor for Authtoken*/
  public Authtoken() {
    this.authtoken=UUID.randomUUID().toString();
    this.username=null;
  }
  public Authtoken(String username){
    this.authtoken =  UUID.randomUUID().toString();
    this.username = username;
  }
  /** Constructor with parameters for Authtoken*/
  public Authtoken(String authtokenInput, String usernameInput){
    this.authtoken = authtokenInput;
    this.username = usernameInput;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
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
    Authtoken p = (Authtoken) o;
    return Objects.equals(p.authtoken, authtoken) && Objects.equals(p.username, username);
  }
}
