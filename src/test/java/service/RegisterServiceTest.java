package service;

import Request.RegisterRequest;
import Response.EventIDResponse;
import Response.RegisterResponse;
import Service.ClearService;
import Service.RegisterService;
import dao.*;
import models.Authtoken;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

  @BeforeEach
  public void setUp() throws DataAccessException {
    ClearService clearService=new ClearService();
    clearService.clear();
  }


  @Test
  public void RegisterpassTest() throws DataAccessException {
    RegisterService regSer=new RegisterService();
    RegisterRequest regReq=new RegisterRequest("TestUser", "Testpass",
            "testemail@email.com", "testfirst", "testlast", "m");
    RegisterResponse regRes=regSer.Register(regReq);
    assertNotNull(regRes.getPersonID());
    assertNotNull(regRes.getAuthtoken());
    assertNotNull(regRes.getUsername());
    assertEquals(null, regRes.getMessage());

  }
  @Test
  public void RegisterFailTest() throws DataAccessException {
    RegisterService regSer=new RegisterService();
    RegisterRequest regReq=new RegisterRequest("TestUser", "Testpass",
            "testemail@email.com", "testfirst", "testlast", "m");
    RegisterResponse regRes=regSer.Register(regReq);
    assertEquals(null, regRes.getMessage());
    regRes=regSer.Register(regReq);
    assertEquals("Error: Username taken", regRes.getMessage());
  }
}