// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  
  String login;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI, String login) 
    throws IOException 
  {
    super(host, port); 
    this.login = login;
    this.clientUI = clientUI;
    openConnection();
  }

  
  
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
    	if (message.startsWith("#")) {
    		handleCommand(message);
    	}
    	
    	sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  public void handleCommand(String cmd) throws IOException {
	  if (cmd.equals("#quit")){
	      quit();
	    }
	    
	  else if (cmd.equals("#logoff")){
	      closeConnection();
	  }
	    
	  else if (cmd.equals("#sethost")){
	      if (!isConnected()){
	        setHost(getHost());
	        clientUI.display("Host Set");
	      }
	      else {
	        clientUI.display(cmd + "cannot be executed as client is still connected");
	      }
	    }
	  
	  else if (cmd.equals("#setport")) {
	      if (!isConnected()){
	        setPort(getPort());
	        clientUI.display("Port set");
	      }
	      else {
	        clientUI.display(cmd +" cannot be executed as client is still connected");
	      }
	    }
	    
	    
	  else if (cmd.equals("#login")) {
	      if (!isConnected()){
	        openConnection();
	        clientUI.display("You have connected to the server. ");
	      }
	  
	      
	      else {
	        clientUI.display(cmd +" cannot be executed as client is still connected");
	      }
	  }
	  
	  else if (cmd.equals("#gethost")) {
		      clientUI.display(getHost());
		    }
		    
	  else if (cmd.equals("#getport")) {
		      clientUI.display(Integer.toString(getPort()));
		    }
	    
}
  
  
  @Override
  protected void connectionClosed() {
	  clientUI.display("Server has shut down");
	  quit();
  }
  
  @Override
  protected void connectionException(Exception exception) {
	  clientUI.display("Server has shut down due to error");
	  quit();
  }
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
