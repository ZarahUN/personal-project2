package fatima;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {
	
	ArrayList<Card> deck = new ArrayList<Card>();

	Server server;
	
	static Logger logger = Logger.getLogger(DeckTest.class.getName());


	 @Before
	  public void setUp()
	  {		  
		  server = new Server(Config.DEFAULT_PORT);
          deck = server.makeDeck(deck);
          	  
          logger.info(String.format("DeckTest Before"));
	  }
	
	@Test
	public void createDeckTest() {
		logger.info(String.format("DeckTest: create a deck"));
		
		boolean empty = deck.isEmpty();
		assertEquals(empty, false);
	}
	
	@Test
	public void deckContentsTest3(){
		logger.info(String.format("DeckTest: confirm deck contents for card 3"));
		
		Card card3 = new Card(3, "purple", 3);
		
		boolean match = true;
		
		if (deck.get(3).getID() != card3.getID()) 	
			match = false;
		else if (deck.get(3).getColour() != card3.getColour()) 
			match = false;
		else if (deck.get(3).getValue() != card3.getValue()) 
			match = false; 
			
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest10() {
		logger.info(String.format("DeckTest: confirm deck contents for card 10"));
		
		Card card10 = new Card(10, "purple", 5);
		
		boolean match = true;
		
		if (deck.get(10).getID() != card10.getID())
			match = false;
		else if (deck.get(10).getColour() != card10.getColour())
			match = false;
		else if (deck.get(10).getValue() != card10.getValue())
			match = false; 
		
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest17() {
		logger.info(String.format("DeckTest: confirm deck contents for card 17"));
		
		Card card17 = new Card(17, "red", 3);

		boolean match = true;
		if (deck.get(17).getID() != card17.getID())
			match = false;
		else if (deck.get(17).getColour() != card17.getColour())
			match = false;
		else if (deck.get(17).getValue() != card17.getValue())
			match = false;

		assertEquals(match, true);
	}
	
	@Test 
	public void deckContentsTest26() {
		logger.info(String.format("DeckTest: confirm deck contents for card 26"));
		
		Card card26 = new Card(26, "red", 5);

		boolean match = true;
		if (deck.get(26).getID() != card26.getID())
			match = false;
		else if (deck.get(26).getColour() != card26.getColour())
			match = false;
		else if (deck.get(26).getValue() != card26.getValue())
			match = false;
	
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest40(){
		logger.info(String.format("DeckTest: confirm deck contents for card 40"));
		
		Card card40 = new Card(40, "blue", 5);
		
		boolean match = true;
		
		if (deck.get(40).getID() != card40.getID())
			match = false;
		else if (deck.get(40).getColour() != card40.getColour())
			match = false;
		else if (deck.get(40).getValue() != card40.getValue())
			match = false; 
		
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest54(){
		logger.info(String.format("DeckTest: confirm deck contents for card 54"));
		
		Card card54 = new Card(54, "yellow", 4);
		
		boolean match = true;
		
		if (deck.get(54).getID() != card54.getID())
			match = false;
		else if (deck.get(54).getColour() != card54.getColour())
			match = false;
		else if (deck.get(54).getValue() != card54.getValue())
			match = false;
		
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest60() {
		logger.info(String.format("DeckTest: confirm deck contents for card 60"));
		
		Card card60 = new Card(60, "green", 1);
		
		boolean match = true;
		
		if (deck.get(60).getID() != card60.getID())
			match = false;
		else if (deck.get(60).getColour() != card60.getColour())
			match = false;
		else if (deck.get(60).getValue() != card60.getValue())
			match = false; 
		
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest71(){
		logger.info(String.format("DeckTest: confirm deck contents for card 71"));
		
		Card card71 = new Card(71, "white", 6);
		
		boolean match = true;
		
		if (deck.get(71).getID() != card71.getID())
			match = false;
		else if (deck.get(71).getColour() != card71.getColour())
			match = false;
		else if (deck.get(71).getValue() != card71.getValue())
			match = false; 
		
		assertEquals(match, true);
	}
	
	@Test
	public void deckContentsTest89(){
		logger.info(String.format("DeckTest: confirm deck contents for card 89"));
		
		Card card89 = new Card(89, "white", 3);
		
		boolean match = true;
					
		if (deck.get(89).getID() != card89.getID())
			match = false;
		else if (deck.get(89).getColour() != card89.getColour())
			match = false;
		else if (deck.get(89).getValue() != card89.getValue())
			match = false; 
		
		assertEquals(match, true);
	}
}
	
