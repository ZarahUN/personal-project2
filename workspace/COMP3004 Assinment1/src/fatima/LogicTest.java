package fatima;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogicTest
{

	private Server server;
	private ServerThread serverThread;
	
	Player[] players;
	Player player1;
	Player player2;
	
	  String input = "y";
	int id;
		  
  @Before
  public void setUp()
  {
	  System.out.println("IN SETUP");
	  server = new Server(Config.DEFAULT_PORT);
	  //Game.setPrevColour("purple");
	  
	  //player1.setId(0);
		//id = player1.getId();
		
		//player2.setId(1);
	  
	  player1 = new Player();
	  player2 = new Player();
	  
	  players = new Player[2];
		players[0] = player1;
		players[1] = player2;
		Game.setPrevColour("purple");
		
	  
  }
  
  @Test
  public void testCheckGameColour1()
  {
	  boolean t = Server.checkGameColour("green");
	  assertEquals(t, true);
  }
  
  @Test
  public void testCheckGameColour2()
  {
	  boolean t = Server.checkGameColour("blue");
	  assertEquals(t, true);
  }

  
  @Test
  public void testCheckGameColour3()
  {
	  boolean t = Server.checkGameColour("red");
	  assertEquals(t, true);
  }

  @Test
  public void testCheckGameColour4()
  {
	  boolean t = Server.checkGameColour("white");
	  assertEquals(t, true);
  }

  @Test
  public void testCheckGameColour5()
  {
	  boolean t = Server.checkGameColour("purple");
	  assertEquals(t, false);
  }

  @Test
  public void testCheckGameColour6()
  {
	  boolean t = Server.checkGameColour("yellow");
	  assertEquals(t, true);
  }

  
  @Test
  public void test2NumPlayers()
  {
	  //Config.MAX_CLIENTS = 2;
	// Server appServer = new Server(Config.DEFAULT_PORT);
	  
	  Client c1 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c2 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c3 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  
	  assertTrue(c1.isConnected());
	  
	  assertTrue(c2.isConnected());
	  
	  
	  assertTrue(c3.isConnected());
	  
	  
	  assertTrue(c1.stop());
	  assertTrue(c2.stop());
	  assertTrue(c3.stop());

//	  assertTrue(appServer.shutdown());
	  
	  //boolean t = 
	  //assertEquals(t, true);	  
  }
  
  @Test
  public void test3NumPlayers()
  {
	  //Config.MAX_CLIENTS = 2;
	// Server appServer = new Server(Config.DEFAULT_PORT);
	  
	  Client c1 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c2 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c3 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c4 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  
	  
	  assertTrue(c1.isConnected());
	  
	  assertTrue(c2.isConnected());
	  
	  
	  assertTrue(c3.isConnected());
	  
	  assertTrue(c4.isConnected());
	  
	  
	  assertTrue(c1.stop());
	  assertTrue(c2.stop());
	  assertTrue(c3.stop());
	  assertTrue(c4.stop());

//	  assertTrue(appServer.shutdown());
	  
	  //boolean t = 
	  //assertEquals(t, true);	  
  }
  
  @Test
  public void test4NumPlayers()
  {
	  //Config.MAX_CLIENTS = 2;
	// Server appServer = new Server(Config.DEFAULT_PORT);
	  
	  Client c1 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c2 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c3 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c4 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  Client c5 = new Client(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	  
	  
	  assertTrue(c1.isConnected());
	  
	  assertTrue(c2.isConnected());
	  
	  
	  assertTrue(c3.isConnected());
	  
	  assertTrue(c4.isConnected());
	  
	  assertTrue(c5.isConnected());
	  
	  
	  assertTrue(c1.stop());
	  assertTrue(c2.stop());
	  assertTrue(c3.stop());
	  assertTrue(c4.stop());
	  assertTrue(c5.stop());

//	  assertTrue(appServer.shutdown());
	  
	  //boolean t = 
	  //assertEquals(t, true);	  
  }
  
  
}
  