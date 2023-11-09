package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


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
    
	if (msgg.startsWith("#login")){
      String loginID = msgg.substring(7,msgg.length()-1);

      client.setInfo(login,loginID);
      System.out.println("<" + client.getInfo(login) + "> has logged on" );
    }
    else{
      this.sendToAllClients(client.getInfo(login) + "> " + msgg);
    }
	
}


public void handleMessageFromServerUI(String message) {
	// TODO Auto-generated method stub
	
}
}
//End of EchoServer class