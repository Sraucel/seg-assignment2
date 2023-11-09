package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import OCSF.OCSF.src.ocsf.server.ConnectionToClient;
import edu.seg2105.client.common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  ChatIF UI;
  
  String login;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF UI) 
  {
    super(port);
    this.UI = UI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A new client has connected,");
	  
  }
  
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("A client has disconnected, say your goodbyes.");
	  
  }
  
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  System.out.println("A client has disconnected.");
	  
  }
  


@Override
protected void handleMessageFromClient(Object msg, ocsf.server.ConnectionToClient client) {
	String msgg = (String)msg;
    
	if (msgg.startsWith("#login") && login != null){
      String loginID = msgg.substring(7,msgg.length()-1);

      client.setInfo(login,loginID);
      System.out.println("Client has logged on");
    }
    else{
      this.sendToAllClients(client.getInfo(login) + msgg);
    }
	
}


public void handleMessageFromServerUI(String message) {
	try {
	      if (message.startsWith("#")){
	        handleCommand(message);
	      }
	      else {
	        sendToAllClients("SERVERMSG" + "> " + message);
	        UI.display(message);
	      }
	    }
	    catch (IOException e){
	    	
	    }
	
}

public void handleCommand(String cmd) throws IOException {
	if (cmd.equals("#quit")){
	      quit();

	    }
	    else if (cmd.equals("#stop")){
	      stopListening();

	    }
	    else if (cmd.equals("#close")){
	      close();


	    }
	    else if (cmd.equals("#setport")) {
	      if (!isListening()){
	        setPort(getPort());
	        UI.display("port set to "+ getPort());
	      }
	      else {
	        UI.display(cmd +" command can not be executed");
	      }
	    }

	    else if (cmd.equals("#getport")) {
	      UI.display(Integer.toString(getPort()));
	    }
	    else if (cmd.equals("#start")) {
	      if (!isListening()){
	        listen();

	      }
	      else {
	        UI.display(cmd +" command can not be executed");
	      }
}
}
public void quit() {
	try {
		close();
	}
	catch(IOException e) {
		System.exit(0);
	}
}
}
//End of EchoServer class
