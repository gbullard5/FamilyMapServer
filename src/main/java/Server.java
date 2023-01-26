import java.io.*;
import java.net.*;

import Handlers.*;
import com.sun.net.httpserver.*;

/*
	This example demonstrates the basic structure of the Family Map Server
	(although it is for a fictitious "Ticket to Ride" game, not Family Map).
	The example is greatly simplfied to help you more easily understand the
	basic elements of the server.

	The Server class is the "main" class for the server (i.e., it contains the
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which
		the server should accept incoming client connections.
*/
public class Server {


  private static final int MAX_WAITING_CONNECTIONS = 12;

  private HttpServer server;

  private void run(String portNumber) {

    System.out.println("Initializing HTTP Server");

    try {
      server = HttpServer.create(
              new InetSocketAddress(Integer.parseInt(portNumber)),
              MAX_WAITING_CONNECTIONS);
    }
    catch (IOException e) {
      e.printStackTrace();
      return;
    }
    server.setExecutor(null);

    System.out.println("Creating contexts");

    server.createContext("/clear", new ClearHandler());
    server.createContext("/event", new EventHandler());
    server.createContext("/event/", new EventIDHandler());
    server.createContext("/fill/", new FillHandler());
    server.createContext("/load", new LoadHandler());
    server.createContext("/user/login", new LoginHandler());
    server.createContext("/person", new PersonHandler());
    server.createContext("/person/", new PersonIDHandler());
    server.createContext("/user/register", new RegisterHandler());
    server.createContext("/", new FileHandler());

    System.out.println("Starting server");

    server.start();

    System.out.println("Server started");
  }

  public static void main(String[] args) {
    String portNumber = args[0];
    new Server().run(portNumber);
  }
}
