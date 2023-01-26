package Handlers;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Response.RegisterResponse;
import Service.LoginService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.security.Provider;

public class RegisterHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String resp = "";
    boolean success = false;
    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        InputStream input=exchange.getRequestBody();
        String request=readString(input);
        Gson gson=new Gson();
        RegisterRequest regReq=gson.fromJson(request, RegisterRequest.class);

        RegisterService regServ=new RegisterService();
        RegisterResponse respData;

        respData=regServ.Register(regReq);
        resp=gson.toJson(respData);

        if (respData.isSuccess()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
        }
        OutputStream respBody=exchange.getResponseBody();
        writeString(resp, respBody);
        respBody.close();
        success=true;
      }


      if (!success) {
        exchange.getResponseBody().close();
      }
    } catch (IOException | DataAccessException d) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      d.printStackTrace();
    }
  }


  private void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    sw.write(str);
    sw.flush();

  }
  private String readString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }
  }

