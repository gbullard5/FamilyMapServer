package Response;

/**Response class for Login*/
public class LoginResponse extends ParentResponse{
  private String username;
  private String authtoken;
  private String personID;

  public LoginResponse(boolean success, String message) {
    super(success, message);
  }
  public LoginResponse(String authToken, String username, String personID, boolean success) {
    super(success, null);
    this.authtoken= authToken;
    this.username = username;
    this.personID = personID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }
}
