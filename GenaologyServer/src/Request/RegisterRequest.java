package Request;

/** Request class for Request*/
public class RegisterRequest {
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;


  /** Constructor with parameters for Request*/
  public RegisterRequest(String usernameInput, String passwordInput, String emailInput,
                         String firstNameInput, String lastNameInput, String genderInput) {
    this.username = usernameInput;
    this.password = passwordInput;
    this.email = emailInput;
    this.firstName = firstNameInput;
    this.lastName = lastNameInput;
    this.gender = genderInput;
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
}
