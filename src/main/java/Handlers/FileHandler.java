package Handlers;

import Response.EventIDResponse;
import Service.EventIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import com.sun.net.httpserver.HttpHandler;


public class FileHandler implements HttpHandler {
  public void handle(HttpExchange exchange) throws IOException {
    try {
    if (exchange.getRequestMethod().toLowerCase().equals("get")) {
        String requestURL=exchange.getRequestURI().toString();
        if(requestURL.equals("/") || requestURL.equals(null)) {
          requestURL = "web/index.html";
        }
        else{
          requestURL = "web/" + requestURL;
        }
        File URL =new File(requestURL);
        if(URL.exists()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream respBody=exchange.getResponseBody();
          Files.copy(URL.toPath(), respBody);
          respBody.close();
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
          OutputStream respBody=exchange.getResponseBody();
          File url =new File("web/HTML/404.html");
          Files.copy(url.toPath(), respBody);
          exchange.getResponseBody().close();
        }
    }else{
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,0);
    }
  } catch (IOException d) {
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
    exchange.getResponseBody().close();
    d.printStackTrace();
  }
}
}
