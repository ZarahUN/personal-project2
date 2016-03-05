package fatima;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class FourPlayerHandTest {
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	
	ArrayList<Card> deck = new ArrayList<Card>();

	Server server;
	
	static Logger logger = Logger.getLogger(DeckTest.class.getName());


	 @Before
	  public void setUp()
	  {		  
		  player1 = new Player();
		  player2 = new Player();
		  player3 = new Player();
		  player4 = new Player();
		  
          Config.MAX_CLIENTS = 4;
		  server = new Server(Config.DEFAULT_PORT);
          deck = server.makeDeck(deck);
          	  
          logger.info(String.format("DeckTest Before"));
	  }
	

	@Test
	public void player1HandTest(){
		logger.info(String.format("DeckTest: confirm player 1 hand"));
		
		server.distributeHands(deck, player1);
				
		boolean playerHands = true; 
		if (player1.getHand() == null || player1.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test 
	public void player2HandTest(){
		logger.info(String.format("DeckTest: confirm player 2 hand"));
		
		server.distributeHands(deck, player2);
				
		boolean playerHands = true;
		if (player2.getHand() == null || player2.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test
	public void player3HandTest(){
		logger.info(String.format("DeckTest: confirm player 3 hand"));
				
		server.distributeHands(deck, player3);
		
		boolean playerHands = true;
		if (player3.getHand() == null || player3.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test
	public void player4HandTest(){
		logger.info(String.format("DeckTest: confirm player 4 hand"));
		
		server.distributeHands(deck, player4);
				
		boolean playerHands = true;
		if (player4.getHand() == null || player4.getHand().isEmpty())
			playerHands = false;
		
		assertEquals(playerHands, true);
	}
	
	@Test
	public void stateTestP1(){
		logger.info(String.format("DeckTest: confirm card states for player 1"));
				
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
		logger.info(String.format("DeckTest: confirm card states for player 2"));
				
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
	
	@Test
	public void stateTestP3(){
		logger.info(String.format("DeckTest: confirm card states for player 3"));
				
		boolean state = true;
		server.distributeHands(deck, player3);
		
		for (int i = 0; i < deck.size(); i++)
		{				
			for (int j = 0; j < player3.getHand().size(); j++)
			{
				if (deck.get(i).equals(player3.getHand().get(j)))
				{
					if (deck.get(i).getState() != 1)
						state = false;
				}		
			}
		}
		assertEquals(state, true);
	}
	
	@Test
	public void stateTestP4(){
		logger.info(String.format("DeckTest: confirm card states for player 4"));
		
		boolean state = true;
		server.distributeHands(deck, player4);
		
		for (int i = 0; i < deck.size(); i++)
		{				
			for (int j = 0; j < player4.getHand().size(); j++)
			{
				if (deck.get(i).equals(player4.getHand().get(j)))
				{
					if (deck.get(i).getState() != 1)
						state = false;
				}
			}
		}
		assertEquals(state, true);
	}
}
