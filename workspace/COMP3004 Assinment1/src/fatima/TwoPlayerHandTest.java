package fatima;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class TwoPlayerHandTest {
	
	Player player1;
	Player player2;
	
	ArrayList<Card> deck = new ArrayList<Card>();

	Server server;
	
	static Logger logger = Logger.getLogger(TwoPlayerHandTest.class.getName());


	 @Before
	  public void setUp()
	  {		  
		  player1 = new Player();
		  player2 = new Player();
		  
          Config.MAX_CLIENTS = 2;
		  server = new Server(Config.DEFAULT_PORT);
          deck = server.makeDeck(deck);
          	  
          logger.info(String.format("TwoPlayerHandTest Before"));
	  }
	
	
	@Test
	public void player1HandTest(){
		logger.info(String.format("TwoPlayerHandTest: confirm player 1 hand"));
		
		server.distributeHands(deck, player1);
				
		boolean playerHands = true; 
		if (player1.getHand() == null || player1.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test 
	public void player2HandTest(){
		logger.info(String.format("TwoPlayerHandTest: confirm player 2 hand"));
		
		server.distributeHands(deck, player2);
				
		boolean playerHands = true;
		if (player2.getHand() == null || player2.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test
	public void stateTestP1(){
		logger.info(String.format("TwoPlayerHandTest: confirm card states for player 1"));
				
		boolean state = true;
		server.distributeHands(deck, player1);
		
		for (int i = 0; i < deck.size(); i++)
		{
			for (int j = 0; j < player1.getHand().size(); j++)
			{
				if (deck.get(i).equals(player1.getHand().get(j)))
				{
					if (deck.get(i).getState() != 1)
						state = false;
				}
			}		
		}
		assertEquals(state, true);
	}
	
	@Test
	public void stateTestP2(){
		logger.info(String.format("TwoPlayerHandTest: confirm card states for player 2"));
				
		boolean state = true;
		server.distributeHands(deck, player2);
		  
		for (int i = 0; i < deck.size(); i++)
		{		
			for (int j = 0; j < player2.getHand().size(); j++)
			{
				if (deck.get(i).equals(player2.getHand().get(j)))
				{
					if (deck.get(i).getState() != 1)
						state = false;
				}
			}		
		}
		assertEquals(state, true);
	}
	
}
