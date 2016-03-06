package fatima;

import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import java.io.*;

public class Server implements Runnable 
{
	static Logger logger = Logger.getLogger(Server.class.getName());
	
	int firstPlayerID = 0;
	private int clientCount = 0;
	private int holder = 0;
	private int num = 0;
	private int tournamentValue;
	private int curPlayer;
	
	private Thread thread = null;
	private ServerSocket server = null;
	
	protected Player[] players;
	protected Player[] tournamentPlayers;
	private HashMap<Integer, ServerThread> clients;
	
	private int MAX_NUMBER_OF_CLIENTS = Config.MAX_CLIENTS;
	
	protected static Boolean gameStarted = Boolean.FALSE;
	protected static GameState gameState = GameState.SETUP;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	//I've set this to Card instead of a general object to simplify things for now
	//Thinking we could probably make action cards their own cards....or inherit...
	
	static Game game;
		
	public Server(int port) {
		try {
			/** Set up our message filter object */
			System.out.println("Binding to port " + port + ", please wait  ...");
			logger.info("Binding to port " + port);
			game = new Game();
			
			/**
			 * I use a HashMap to keep track of the client connections and their
			 * related thread
			 */
			clients = new HashMap<Integer, ServerThread>();
			
			/** Establish the servers main port that it will listen on */
			server = new ServerSocket(port);
			/**
			 * Allows a ServerSocket to bind to the same address without raising
			 * an "already bind exception"
			 */
			logger.info(String.format("Setting number of players to %d",Config.MAX_CLIENTS));
			players = new Player[Config.MAX_CLIENTS];
			tournamentPlayers = new Player[Config.MAX_CLIENTS];
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
			logger.fatal(Formatter.exception("Contructor", ioe));
		}
	}
	
	public void setNumberOfClients (int numClients) {
		this.MAX_NUMBER_OF_CLIENTS = numClients;
	}

	/** Now we start the servers main thread */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();			
			logger.info(String.format("Server started: %s %d", server,thread.getId()));
		}
	}

	/** The main server thread starts and is listening for clients to connect */
	public void run() {
		while (thread != null) {
			try {
				logger.info("Waiting for a client ...");
				addThread(server.accept());
			} catch (IOException ioe) {				
				logger.fatal(Formatter.exception("run()", ioe));
			}}
	}

	/** 
	 * Client connection is accepted and now we need to handle it and register it 
	 * and with the server | HashTable 
	 **/
	private void addThread(Socket socket) {
		logger.info(Formatter.format("Client Requesting connection: " , socket));
		
		if (clientCount < Config.MAX_CLIENTS) {
			try {
				/** Create a separate server thread for each client */
				ServerThread serverThread = new ServerThread(this, socket);
				/** Open and start the thread */
				serverThread.open();
				serverThread.start();
				
				clients.put(serverThread.getID(), serverThread);		
				logger.info(Formatter.format("Client Accepted: " , socket));
				
				Player player = new Player(serverThread.getID(), serverThread);
				players[clientCount] = player;
				this.clientCount++;
				
				serverThread.send(String.format("[%s:%d] Welcome to the game. Your port id is %s:%d\n", server.getInetAddress(), server.getLocalPort(), serverThread.getSocketAddress(), serverThread.getID()));				
				serverThread.send(String.format("[%s:%d] Please Enter in your user name:\n",serverThread.getSocketAddress(), serverThread.getID()));

			} catch (IOException ioe) {
				logger.fatal(Formatter.exception("addThread(Socket)", ioe));
			}
		} else {
			logger.info(Formatter.format("Client Tried to connect:", socket));
			logger.info(Formatter.format("Client refused: maximum number of clients reached:", Config.MAX_CLIENTS));
		}
	}

	protected void setPlayersName (int portId, String name) {
		logger.info(String.format("Setting players name to %s on port id %d",name,portId));
		logger.info(String.format("Number of registered players          %d",players.length));
		for (int i = 0; i < Config.MAX_CLIENTS; i++) {
			if (players[i] != null) {
				
				    //testing: testing portIDs
					System.out.println(String.format("TESTING setPlayersName: portID is %d and Player %d portID is %d\n", portId, i+1, players[i].getId()));
					
					logger.info(String.format("Checking players port id %d",players[i].getId()));
					if (players[i].getId() == portId) {
						players[i].setName(name);
						
						//testing: making sure the correct players name have been set based on the clients input
						System.out.println(String.format("TESTING setPlayersName: players %d name set to %s \n", i+1, players[i].getName()));
						
						logger.info(String.format("Players name set to %d %s",portId, players[i].getName()));
						ServerThread thread = clients.get(portId);			
						logger.info(String.format("Sending message back to player on port %d",thread.getID()));
						holder++;
						if (holder == Config.MAX_CLIENTS){
							holder = 0;
							gameState = GameState.FINISHED;
						}}}}
	}
	
	protected void distributeHands(ArrayList<Card> d, Player p)
	{
		int start = 0, end = 7;
		boolean goodDeck = false;
		while (goodDeck == false) //keep shuffling until the first 8 cards have state 0
		{
			int count = 0;
			Collections.shuffle(d); //this should shuffle the deck
			logger.info(String.format("The deck has been shuffled"));
			for (int i = start; i < end + 1; i++)
			{
				if (d.get(i).getState() == 0)
					count++;
			}
			if (count == 8) 
				goodDeck = true;
			logger.info(String.format("Hand distributed to %s", p.getName()));
		}
		
		//distribute cards
		p.setHand(new ArrayList<Card>(d.subList(start, end))); //This should cast from list to arraylist
		for(int j = start; j < end+1; j++)
		{
			d.get(j).setState(1); //change state to "in player hand"
			logger.info(String.format("The state of %s hand has been changed to 1", p.getName()));
		}
	}
	
	protected ArrayList<Card> makeDeck(ArrayList<Card> d)
	{
		logger.info(String.format("testing makeDeck: Making purple cards of value 3"));
		int i;
		//purple cards
		//1 - 4: 4 purple of value 3
		for (i = 0; i < 4; i++)
			d.add(new Card(i, "purple", 3));
		
		
		logger.info(String.format("testing makeDeck: Making purple cards of value 4"));
		//5 - 8: 4 purple of value 4
		for (i = 4; i < 8; i++)
			d.add(new Card(i, "purple", 4));
		
		logger.info(String.format("testing makeDeck: Making purple cards of value 5"));
		//9 - 13: 4 purple of value 5
		for (i = 8; i < 12; i++)
			d.add(new Card(i, "purple", 5));
		
		logger.info(String.format("testing makeDeck: Making purple cards of value 7"));
		//13 - 14: 2 purple of value 7
		for (i = 12; i < 14; i++)
			d.add(new Card(i, "purple", 7));
		
		logger.info(String.format("testing makeDeck: Making red cards of value 3"));
		//red cards
		//15 - 20: 6 red of value 3
		for (i = 14; i < 20; i++)
			d.add(new Card(i, "red", 3));
		
		logger.info(String.format("testing makeDeck: Making red cards of value 4"));
		//20 - 25: 6 red of value 4
		for (i = 20; i < 26; i++)
			d.add(new Card(i, "red", 4));
		
		logger.info(String.format("testing makeDeck: Making red cards of value 5"));
		//26 - 27: 2 red of value 5
		for (i = 26; i < 28; i++)
			d.add(new Card(i, "red", 5));
		
		logger.info(String.format("testing makeDeck: Making blue cards of value 2"));
		//blue cards
		//28 - 31: 4 blue of value 2
		for (i = 28; i < 32; i++)
			d.add(new Card(i, "blue", 2));
		
		logger.info(String.format("testing makeDeck: Making blue cards of value 3"));
		//32 - 35: 4 blue of value 3
		for (i = 32; i < 36; i++)
			d.add(new Card(i, "blue", 3));
		
		logger.info(String.format("testing makeDeck: Making blue cards of value 4"));
		//36 - 39: 4 blue of value 4
		for (i = 36; i < 40; i++)
			d.add(new Card(i, "blue", 4));
		
		logger.info(String.format("testing makeDeck: Making blue cards of value 5"));
		//40 - 41: 2 blue of value 5
		for (i = 40; i < 42; i++)
			d.add(new Card(i, "blue", 5));
		
		logger.info(String.format("testing makeDeck: Making yellow cards of value 2"));
		//yellow cards
		//42 - 45: 4 yellow of value 2
		for (i = 42; i < 46; i++)
			d.add(new Card(i, "yellow", 2));
		
		logger.info(String.format("testing makeDeck: Making yellow cards of value 3"));
		//46 - 53: 8 yellow of value 3
		for (i = 46; i < 54; i++)
			d.add(new Card(i, "yellow", 3));
		
		logger.info(String.format("testing makeDeck: Making yellow cards of value 4"));
		//54 - 55: 2 yellow of value 4
		for (i = 54; i < 56; i++)	
			d.add(new Card(i, "yellow", 4));
		
		logger.info(String.format("testing makeDeck: Making green cards of value 1"));
		//Green cards
		//56 - 70: 14 green of value 1
		for (i = 56; i < 70; i++)
			d.add(new Card(i, "green", 1));
		
		logger.info(String.format("testing makeDeck: Making white cards of value 6"));
		//supporter
		//71 - 74: 4 white of value 6
		//These are the maidens - there are reprecussions for using this and then
		//withdrawing
		for (i = 70; i < 74; i++)
			d.add(new Card(i, "white", 6));
		
		logger.info(String.format("testing makeDeck: Making white cards of value 2"));
		//75 - 82: 8 white of value 2
		for (i = 74; i < 82; i++)
			d.add(new Card(i, "white", 2));
		
		logger.info(String.format("testing makeDeck: Making white cards of value 3"));
		//83 - 90: 8 white of value 3
		for (i = 82; i < 90; i++)
			d.add(new Card(i, "white", 3));
		
	
		logger.info(String.format("testing makeDeck: Deck has been successfully made"));
		return d;
	}
	
	protected void startNewGame (int ID, String input) {
		
		//testing the startNewGame
		System.out.println("Testing: START NEW GAME");
		if (input.equalsIgnoreCase("N"))
			shutdown();
		deck = makeDeck(deck);
		
		logger.info(String.format("TESTING Start New Game: Deck has been made"));	
		
		for (int i = 0; i < Config.MAX_CLIENTS; i++)
		{
			distributeHands(deck, players[i]);
			logger.info(String.format("testing Start New Game: cards has been distributed to player %s", players[i].getName()));
		}
		//We're going to have to do something like this elsewhere too for the next round
		//Tournament?
		
		game.setWinner(players[0]);
		
		
		logger.info(String.format("Testing StartNewGame: Winner of previous game is %s", players[0].getName()));
		
		for(int i=0; i < Config.MAX_CLIENTS; i++)
		{
			ServerThread thread = players[i].serverThread; //entity.getValue();
			if(players[i] == game.getWinner())
			{
				logger.info(String.format("Winner is asked to pick the colour of the game"));
				thread.send("What colour would you like the game to be?\n :");
			    gameState = GameState.SET_GAME_COLOUR;
			}
			else
				thread.send("Waiting for winner to select colour.\n");
		  }
		logger.info(String.format("Testing StartNewGame: exiting startNewGame"));
	}
	
	void setGameColour(String input) 
	{
		game.setColour(input);
		logger.info(String.format("testing setGameColour: Colour of the game has been set to: %s", game.getColour()));
		
	   	for (int i = 0; i < Config.MAX_CLIENTS; i++) 
		{
        	ServerThread thread = players[i].serverThread;//entity.getValue();

			logger.info(String.format("TESTING setGAmeColour: Asking players if they want to join tournament"));
	        thread.send(String.format("Would you like to join the tournament (Y or N)? Tournament colour is:%s\n", game.getColour()));
	        thread.send(String.format("Your hand is: \n"));
	        logger.info(String.format("Testing setGameColour: showing players their hand"));
	        for(int k = 0; k < players[i].getHand().size(); k++)
			{
				thread.send(String.format("%s: %s card of value %d\n",players[i].getName(), players[i].getHand().get(k).getColour(), players[i].getHand().get(k).getValue()));
			}
		}
	   	
	   	logger.info(String.format("Testing setGameColour: Exiting"));
	   	gameState = GameState.JOIN_TOURNAMENT;
	}
		
	
	static boolean checkGameColour(String input)
	{
		if (Game.getPrevColour() == "purple" && input == "purple")
		{
			return false;
		}
		return true;
	}
	
	public void joinTournament(int id, String input) {
		int j; 
		
		tournamentValue = 0; //reset 
		logger.info(String.format("Testing joinTournament: tournament value has been reset"));
		
        for (int i = 0; i < Config.MAX_CLIENTS; i++) 
        {
        	j = 0;
        	ServerThread thread = players[i].serverThread;//entity.getValue();
        	if (players[i].getId() == id) {
		  		if (input.equalsIgnoreCase("Y"))
		  		{
		  			logger.info(String.format("Testing joinTournament: player %d answers yes", i+1));
		  			while (j < players[i].getHand().size()) {
		  				if (players[i].getHand().get(j).getColour().equals(game.getColour()) || players[i].getHand().get(j).getColour() == "white") 
		  				{
		  					logger.info(String.format("Testing joinTournament: checking if player has the right colour card in hand"));
		  					if (players[i].equals(game.getWinner()))
		  						curPlayer = holder;
		  					Player player = new Player(players[i].getId(), players[i].serverThread);
		  					player.setName(players[i].getName());
		  					player.setHand(players[i].getHand());
		  					player.setNumPoints(0);
		  					tournamentPlayers[holder] = player;
					  		holder++;
			  				thread.send("You have successfully entered the tournament.\n");
			  				//player that has joined the tournament
							System.out.println("Player " + i + " named : " + tournamentPlayers[i].getName() + "has sunccessfully joined the tournament");
							logger.info(String.format("Testing joinTournament: %s has successfully joined the tournament", tournamentPlayers[i].getName()));
			  				j = players[i].getHand().size();
			  				//if player has at least one card of the specified game color or a white card, they can enter
		  				}
		  				else
		  					j++;
		  			}
		  		}
		  		else {
		  			thread.send("You will not be added to the tournament.\n");
		  			holder++;
		  
		  		}	
		  		System.out.println("Holder: " + holder);
	        }
	    }
        if (holder == Config.MAX_CLIENTS) {
        	holder = 0;
        	//if (enter) 
        	logger.info(String.format("Testing joinTournament: Maximum  number of players to enter tournament reached. Tournament 1 has begun"));
        	  tournament_1(tournamentPlayers[curPlayer]);
        	/*else 
        		withdrawTournament(id, "W");*/
        }
	}
	
	public void withdrawTournament(int id, String input)
	{
		logger.info(String.format("Testing withdrawTournament: in withdrawTournamnet function"));
		if (input.equalsIgnoreCase("W"))
		{
			for (int i = 0; i < players.length; i++)	
			{
				if (players[i].getId() == id) {
					ServerThread thread = players[i].serverThread;
					thread.send("You will be removed from the tournament");
					logger.info(String.format("Testing withdrawTournament: %s has choosen to withdraw from the tournament", players[i].getName()));
					//removes all cards from players hand and sets value of card in deck to 0
					for(int k = 0; k < deck.size(); k++)
					{
						for(int j = 0; j < players[i].getHand().size(); j++)
						{
							logger.info(String.format("Testing withdrawTournament: removing cards from players hand and puts it back in deck"));
							System.out.println("player I: " + i);
							System.out.println("checking cards in players hand J: " + j);
							System.out.println("checking cards in deckK: " + k);
							System.out.println("Player card: " + players[i].getHand().get(j).getID());
							System.out.println("Deck Card: " + deck.get(k).getID());
							
							if(players[i].getHand().get(j).getID() == deck.get(k).getID())
								deck.get(k).setState(0);
							players[i].getHand().remove(j);
						}
					}
					
					//call distributeHands
					logger.info(String.format("Testing withdrawTournament: cards are distributed between players again"));
					distributeHands(deck, players[i]);
					int nextPlayer = 0;
					for (int j = 0; j < tournamentPlayers.length; j++) {
						if (players[i].getId() == tournamentPlayers[j].getId()) {
							
							removePlayer(tournamentPlayers[j]);
							logger.info(String.format("Testing withdrawTournament: %s has been removed", tournamentPlayers[j].getName()));
							nextPlayer = j + 1;
						}
					}
					if (nextPlayer >= tournamentPlayers.length)
						nextPlayer = 0;
					tournament_1(tournamentPlayers[nextPlayer]);
				}
			}
		}
		else 
		{
			tournament_1(tournamentPlayers[curPlayer]);
		}
	}
	
	public boolean removePlayer(Player player) 
	{
		int numPlayers = tournamentPlayers.length;
		int count = 0;
		Player[] result = new Player[numPlayers - 1];
        for (int i = 0; i < numPlayers; i++) 
        {
        	if (tournamentPlayers[i] != player)
        	{
	        	Player p = new Player(tournamentPlayers[i].getId(), tournamentPlayers[i].serverThread);
				p.setName(tournamentPlayers[i].getName());
				p.setHand(tournamentPlayers[i].getHand());
				p.setNumPoints(0);
	        	result[count] = p;
	        	count++;
            }
        }
        tournamentPlayers = null; 
        tournamentPlayers = result.clone();
        logger.info(String.format("Testing removePlayer: %s has been removed", player.getName()));
     return true;
     }
	
	
	public void tournament_1(Player p) {
		ServerThread thread = p.serverThread;
		logger.info(String.format("Testing tournament_1: first tournament begins"));
			if (tournamentPlayers.length >= 2)	//there are still 2 players in the tournament
			{
				//Player picks up a  card
				logger.info(String.format("Testing tournament_1: player picks a card from deck"));
				for (int j = 0; j < deck.size(); j++)
				{
					if (deck.get(j).getState() == 0) {
						num = j;
						j = deck.size();
					}
				}
				p.addHand(deck.get(num));
				deck.get(num).setState(1);			//later we will need to check if the deck is empty and then reshuffle the discard pile
					
				//Player views their hand
				thread.send(String.format("Your hand is: \n"));
				for(int k = 0; k < p.getHand().size(); k++)
				{
					if (p.getHand().get(k).getState() == 3)
						thread.send(String.format("ALREADY IN PLAY: %s card of value %d.\n",p.getHand().get(k).getColour(), p.getHand().get(k).getValue(), p.getHand().get(k).getID()));
					else
						thread.send(String.format("%s card of value %d. ID is %d\n",p.getHand().get(k).getColour(), p.getHand().get(k).getValue(), p.getHand().get(k).getID()));
				}
				thread.send(String.format("Current value to beat is %d\n", tournamentValue));
					
				logger.info(String.format("Testing tournament_1: asking player which card(s) they want"));
				//Ask player which card(s) they want to play
				thread.send("Which card(s) do you want to play? Enter the IDs, separated by commas\n");
				gameState = GameState.PROCESS_1;

			}
			else 
			{
				game.setWinner(p);
				logger.info(String.format("Testing tournament_1: winner of first tournament is %s", game.getWinner()));
				if (p.addShield(game.getColour()) == true) {
					logger.info("Game Winner");
					for (int i = 0; i < players.length; i++) {
						players[i].serverThread.send(String.format("Winner of the game is %s", game.getWinner()));
						gameState = GameState.FINISHED;
					}
				}
				else {
					tournamentPlayers = null;
					tournamentPlayers = new Player[Config.MAX_CLIENTS];
					logger.info(String.format("Testing tournament_1: Winner is asked to pick the colour of the game"));
					thread.send("You won the tournament!! What colour would you like the game to be?\n :");
					gameState = GameState.SET_GAME_COLOUR;
				}
			}
	}
	
	public void tournament_2(int id, String input) {
		for (int i = 0; i < tournamentPlayers.length; i++)	//cycles through turns
		{
			int valueHolder = tournamentValue;
			ServerThread thread = players[i].serverThread;
			if (players[i].getId() == id) {
				String[] cardIds = input.split(",");
				
				//check that the cards are appropriate
				for (int j = 0; j < cardIds.length; j++) {
					for (int k = 0; k < deck.size(); k++) {
						if (Integer.valueOf(cardIds[j]).equals(deck.get(k).getID()) && (deck.get(k).getColour().equals(game.getColour()) || deck.get(k).getColour() == "white")) {
							//if cards are appropriate, add their values.
							tournamentPlayers[curPlayer].setNumPoints(tournamentPlayers[curPlayer].getNumPoints() + deck.get(k).getValue());
							deck.get(k).setState(3);
							for (int m = 0; m < tournamentPlayers[curPlayer].getHand().size(); m++) {
								if (tournamentPlayers[curPlayer].getHand().get(m).getID() == deck.get(k).getID())
									tournamentPlayers[curPlayer].getHand().get(m).setState(3);
							}
						}
						else if (Integer.valueOf(cardIds[j]).equals(deck.get(k).getID()) && (!deck.get(k).getColour().equals(game.getColour()) || deck.get(k).getColour() != "white")) {
							tournamentValue = valueHolder;
							thread.send(String.format("You cannot use card of ID %s\n", cardIds[j]));
							tournament_1(players[i]);
						}
					}
				}
				if (tournamentPlayers[curPlayer].getNumPoints() > tournamentValue) {
					tournamentValue = tournamentPlayers[curPlayer].getNumPoints();
					//holder++;
					thread.send("End of turn\n");
				}
				else {
					tournamentValue = valueHolder;
					thread.send("Your card values are not high enough to beat the other players\n");
					tournament_1(players[i]);
				}
			}
		}
		curPlayer++; 
		if (curPlayer == tournamentPlayers.length)
			curPlayer = 0;
		tournament_3(tournamentPlayers[curPlayer]);
	}
	
	public void tournament_3(Player p) {
		ServerThread thread = p.serverThread;

		thread.send(String.format("Do you want to withdraw (w) or play a card? (p) Current value to beat is %d\n", tournamentValue));
		
		gameState = GameState.PROCESS_2;
	}
	
	protected Boolean checkDisconnent (int ID, String input) {
		if (input.equals("quit!")) 
		{
			logger.info(Formatter.format("Removing Client:", ID));
			if (clients.containsKey(ID)) {
				clients.get(ID).send("quit!" + "\n");
				remove(ID);
				return Boolean.TRUE;
			}}
		return Boolean.FALSE;
	}
	
	protected Boolean checkShutdown (int ID, String input) {
		if (input.equals("shutdown!")) { shutdown();	}
		return Boolean.FALSE;
	}
	
	/** Try and shutdown the client cleanly */
	public synchronized void remove(int ID) {
		if (clients.containsKey(ID)) {
			ServerThread toTerminate = clients.get(ID);
			clients.remove(ID);
			clientCount--;

			toTerminate.close();
			toTerminate = null;
		}
	}

	/** Shutdown the server cleanly */
	public void shutdown() {
		Set<Integer> keys = clients.keySet();

		if (thread != null) { thread = null; }

		try {
			for (Integer key : keys) {
				ServerThread thread = clients.get(key);
				thread.send("Shutting down game...");
				thread.close();
				thread = null;
			}
			clients.clear();
			server.close();
			server = null;
		} catch (IOException ioe) {
			logger.fatal(Formatter.exception("shutdown()", ioe));
		}
		logger.info(String.format("Server Shutdown cleanly %s", server));
	}
	
	
	
}