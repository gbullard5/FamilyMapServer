package Response;

/**Response class for Register*/
public class RegisterResponse extends ParentResponse{
  private String authtoken;
  private String username;
  private String personID;

  public RegisterResponse( String message, boolean success) {
    super(message, success);
  }
  public RegisterResponse(String authtoken, String username, String personid, boolean success) {
    super(null, success);
    this.username = username;
    this.personID = personid;
    this.authtoken = authtoken;
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

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }
}
