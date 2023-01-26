package Handlers;

import Response.EventIDResponse;
import Response.EventResponse;
import Response.Serializers.EventSerializer;
import Service.EventIDService;
import Service.EventService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventIDHandler implements HttpHandler {
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
          String eID = requestURL[2];
          GsonBuilder gsonBuilder = new GsonBuilder();
          gsonBuilder.registerTypeAdapter(EventIDResponse.class, new EventSerializer());
          Gson gson= gsonBuilder.create();
          EventIDService event=new EventIDService();
          EventIDResponse respData;

          respData=event.eventID(eID, authToken);
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
    } catch (IOException d) {
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
