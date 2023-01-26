package service;

import Response.FillResponse;
import Service.ClearService;
import Service.FillService;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import models.Authtoken;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffrequest.FillRequest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
Database db = new Database();
  @BeforeEach
  public void setup() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();
    Connection conn=db.getConnection();
    db.clear();
    AuthTokenDao aDao = new AuthTokenDao(conn);
    aDao.insert(new Authtoken("username1","token1"));
    aDao.insert(new Authtoken("username2","token2"));
    UserDao uDao = new UserDao(conn);
    User bestUser= new User("token1", "pass5", "email@email.com", "Steve", "Scott",
            "m", "ba53j-3d");
    uDao.insert(bestUser);
    db.closeConnection(true);

  }
  @Test
  public void fillTestPass() throws DataAccessException {
    FillService fillService = new FillService();
    FillResponse fillResponse =fillService.fill("token1",4);
    assertEquals("Successfully added 31 persons and 91 events to the database.", fillResponse.getMessage() );
  }

  @Test
  public void fillTestFail() throws DataAccessException {
    FillService fillService = new FillService();
    FillResponse fillResponse =fillService.fill("token3",4);
    assertEquals("Error: Invalid username", fillResponse.getMessage() );
  }

}