package fatima;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogicTest
{

  @Before
  public void setUp()
  {
	  
	  //Game.setPrevColour("purple");
	  
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
  