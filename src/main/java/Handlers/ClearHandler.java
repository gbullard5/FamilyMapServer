package Handlers;

import Response.ClearResponse;
import Response.EventResponse;
import Service.ClearService;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import passoffresult.ClearResult;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
  boolean success = false;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String resp = "";
    try{
    if (exchange.getRequestMethod().toLowerCase().equals("post")){
      Gson gson = new Gson();
      ClearService cs = new ClearService();
      ClearResponse respData;

      respData = cs.clear();
      resp = gson.toJson(respData);

      if(resp.toLowerCase().contains("error")){
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

      }else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
      }
      OutputStream respBody = exchange.getResponseBody();
      writeString(resp, respBody);
      respBody.close();
      success = true;
      }

    if(!success){
      exchange.getResponseBody().close();
    }
  }catch(IOException | DataAccessException d){
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




