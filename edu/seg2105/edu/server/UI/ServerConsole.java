package edu.seg2105.edu.server.UI;

import java.io.IOException;
import java.util.Scanner;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.ChatIF;
import edu.seg2105.client.ui.ClientConsole;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF{


	final public static int DEFAULT_PORT = 5555;
	
	EchoServer server;
	
	Scanner Console;
	

	
	public ServerConsole(int port) 
	{
		Console = new Scanner(System.in); 
		server = new EchoServer(port, this);
	    
		try
	    {
	      server.listen(); //Start listening for connections
	    }
	    
		catch (Exception ex)
	    {
	      System.out.println("Did not find clients.");
	    }
	    
	}
	
	 
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = Console.nextLine();
	        server.handleMessageFromServerUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	    	ex.printStackTrace();
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }
	  
	  @Override
	  public void display(String message) 
	  {
		  System.out.println("SERVER MSG> " + message);
	  }
	
	
	
	  public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    ServerConsole sv = new ServerConsole(port);
	    sv.accept();
	  }


	
}