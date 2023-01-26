package Service;

import Response.ClearResponse;
import dao.*;

import javax.xml.crypto.Data;
import java.sql.Connection;

/**Result class for Clear*/
public class ClearService {
  public ClearService() {
  }

  /**
   * function clears data
   */
  public ClearResponse clear() throws DataAccessException {
    try {
      Database data=new Database();
      Connection conn=data.getConnection();
      AuthTokenDao auth=new AuthTokenDao(conn);
      auth.clear();
      EventDao event=new EventDao(conn);
      event.clear();
      PersonDao person=new PersonDao(conn);
      person.clear();
      UserDao user=new UserDao(conn);
      user.clear();
      ClearResponse result=new ClearResponse(true, "Clear succeeded.");
      data.closeConnection(true);
      return result;
    } catch (DataAccessException d) {
      return new ClearResponse(false, "Error: Internal server error");
    }
  }
}
