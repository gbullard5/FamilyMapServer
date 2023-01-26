package service;

import Request.LoginRequest;
import Response.LoginResponse;
import Service.LoginService;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  Database db=new Database();


  @BeforeEach
  public void setup() throws DataAccessException {
    Connection conn=db.getConnection();
    db.clear();
    UserDao uDao = new UserDao(conn);
    User bestUser= new User("theuser", "pass5", "email@email.com", "Steve", "Scott",
            "m", "ba53j-3d");
    uDao.insert(bestUser);
    db.closeConnection(true);
  }
  @Test
  public void loginTestPass() throws DataAccessException {
    LoginService loginService=new LoginService();
    LoginResponse loginResponse=loginService.login(new LoginRequest("pass5", "theuser"));
    assertTrue(loginResponse.getUsername().equals("theuser"));
    assertNotNull(loginResponse.getPersonID());
    assertNotNull(loginResponse.getAuthtoken());
  }

@Test
  public void loginTestFail() throws DataAccessException {
    LoginService loginService=new LoginService();
    LoginResponse loginResponse=loginService.login(new LoginRequest("pass5", "theuser7"));
    assertEquals("Error: Username not found", loginResponse.getMessage());
  }
}