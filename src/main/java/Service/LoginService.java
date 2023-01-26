package Service;

import Request.LoginRequest;
import Response.EventResponse;
import Response.LoginResponse;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import models.Authtoken;
import models.User;

import java.sql.Connection;

/**Result class for Login*/
public class LoginService {
  private Authtoken authtoken = new Authtoken();
  private User userSearch = new User();
  /**Function takes input and returns LoginResponse*/
  public LoginResponse login(LoginRequest r) throws DataAccessException {
    try {
      Database data=new Database();
      Connection conn=data.getConnection();
      AuthTokenDao auth=new AuthTokenDao(conn);
      UserDao auth2=new UserDao(conn);
      if (r.getUsername() == null || r.getPassword() == null) {
        data.closeConnection(false);
        return new LoginResponse(false, "Error: Invaild input");
      }
      userSearch = auth2.find(r.getUsername());
      if(userSearch == null){
        data.closeConnection(false);
        return new LoginResponse(false, "Error: Username not found");
      }
      else if(!userSearch.getPassword().equals(r.getPassword())) {
        data.closeConnection(false);
        return new LoginResponse(false, "Error: Incorrect Password");
      }else{
        authtoken.setUsername(r.getUsername());
        auth.insert(authtoken);
        data.closeConnection(true);
        return new LoginResponse(authtoken.getAuthtoken(),userSearch.getUsername(), userSearch.getPersonID(), true);
      }
    }catch(DataAccessException d){
      return new LoginResponse(false, "Error: Internal server error");
    }
  }
}
