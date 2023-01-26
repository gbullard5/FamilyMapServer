package Request;

/** Request class for Login*/
public class LoginRequest {
  private String username;
  private String password;


  /** Constructor with parameters for Login*/
  public LoginRequest(String passwordInput, String usernameInput){
    this.password = passwordInput;
    this.username = usernameInput;
  }

  //getters and setters
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
}
