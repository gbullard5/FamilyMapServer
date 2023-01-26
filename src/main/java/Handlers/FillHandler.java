package Handlers;

import Request.LoadRequest;
import Response.FillResponse;
import Response.LoadResponse;
import Service.FillService;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import passoffrequest.FillRequest;

import java.io.*;
import java.net.HttpURLConnection;

import static java.lang.Integer.parseInt;

public class FillHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String resp = "";
    boolean success = false;
    try{
      if (exchange.getRequestMethod().toLowerCase().equals("post")){
        Gson gson = new Gson();
        String[] params = exchange.getRequestURI().toString().split("/");
        int gen;
        if(params.length == 4){
          gen = parseInt(params[3]);
        }else{
          gen = 4;
        }
        FillService fillServ = new FillService();
        FillResponse respData;

        respData = fillServ.fill(params[2],gen);
        resp = gson.toJson(respData);

        if(respData.isSuccess()){
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
        }else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
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

