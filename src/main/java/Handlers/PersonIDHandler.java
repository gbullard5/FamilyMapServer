package Handlers;

import Response.EventIDResponse;
import Response.PersonIDResponse;
import Service.EventIDService;
import Service.PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonIDHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String resp="";
    boolean success=false;
    try {
      if (exchange.getRequestMethod().toLowerCase().equals("get")) {
        Headers reqHeaders=exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {
          String authToken=reqHeaders.getFirst("Authorization");
          String[] requestURL=exchange.getRequestURI().toString().split("/");
          String pID = requestURL[2];
          Gson gson=new Gson();
          PersonIDService person=new PersonIDService();
          PersonIDResponse respData;

          respData=person.personID(pID, authToken);
          resp=gson.toJson(respData);

          if (resp.toLowerCase().contains("error")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          }else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
          }
          OutputStream respBody=exchange.getResponseBody();
          writeString(resp, respBody);
          respBody.close();
          success=true;
        }

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
  }

