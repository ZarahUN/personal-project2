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
					System.out.println(String.format("%d %d\n", portId, players[i].getId()));
					logger.info(String.format("Checking players port id %d",players[i].getId()));
					if (players[i].getId() == portId) {
						players[i].setName(name);
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
			for (int i = start; i < end + 1; i++)
			{
				if (d.get(i).getState() == 0)
					count++;
			}
			if (count == 8) 
				goodDeck = true;
		}
		
		//distribute cards
		p.setHand(new ArrayList<Card>(d.subList(start, end))); //This should cast from list to arraylist
		for(int j = start; j < end+1; j++)
		{
			d.get(j).setState(1); //change state to "in player hand"
		}
	}
	
	protected ArrayList<Card> makeDeck(ArrayList<Card> d)
	{
		int i;
		//purple cards
		//1 - 4: 4 purple of value 3
		for (i = 0; i < 4; i++)
			d.add(new Card(i, "purple", 3));
		
		//5 - 8: 4 purple of value 4
		for (i = 4; i < 8; i++)
			d.add(new Card(i, "purple", 4));
		
		//9 - 13: 4 purple of value 5
		for (i = 8; i < 12; i++)
			d.add(new Card(i, "purple", 5));
		
		//13 - 14: 2 purple of value 7
		for (i = 12; i < 14; i++)
			d.add(new Card(i, "purple", 7));
		
		//red cards
		//15 - 20: 6 red of value 3
		for (i = 14; i < 20; i++)
			d.add(new Card(i, "red", 3));
		
		//20 - 25: 6 red of value 4
		for (i = 20; i < 26; i++)
			d.add(new Card(i, "red", 4));
		
		//26 - 27: 2 red of value 5
		for (i = 26; i < 28; i++)
			d.add(new Card(i, "red", 5));
		
		//blue cards
		//28 - 31: 4 blue of value 2
		for (i = 28; i < 32; i++)
			d.add(new Card(i, "blue", 2));
		
		//32 - 35: 4 blue of value 3
		for (i = 32; i < 36; i++)
			d.add(new Card(i, "blue", 3));
		
		//36 - 39: 4 blue of value 4
		for (i = 36; i < 40; i++)
			d.add(new Card(i, "blue", 4));
		
		//40 - 41: 2 blue of value 5
		for (i = 40; i < 42; i++)
			d.add(new Card(i, "blue", 5));
		
		//yellow cards
		//42 - 45: 4 yellow of value 2
		for (i = 42; i < 46; i++)
			d.add(new Card(i, "yellow", 2));
		
		//46 - 53: 8 yellow of value 3
		for (i = 46; i < 54; i++)
			d.add(new Card(i, "yellow", 3));
		
		//54 - 55: 2 yellow of value 4
		for (i = 54; i < 56; i++)	
			d.add(new Card(i, "yellow", 4));
		
		//Green cards
		//56 - 70: 14 green of value 1
		for (i = 56; i < 70; i++)
			d.add(new Card(i, "green", 1));
		
		//supporter
		//71 - 74: 4 white of value 6
		//These are the maidens - there are reprecussions for using this and then
		//withdrawing
		for (i = 70; i < 74; i++)
			d.add(new Card(i, "white", 6));
		
		//75 - 82: 8 white of value 2
		for (i = 74; i < 82; i++)
			d.add(new Card(i, "white", 2));
		
		//83 - 90: 8 white of value 3
		for (i = 82; i < 90; i++)
			d.add(new Card(i, "white", 3));
		
	
		
		return d;
	}
	
	protected void startNewGame (int ID, String input) {
		System.out.println("START NEW GAME");
		if (input.equalsIgnoreCase("N"))
			shutdown();
		deck = makeDeck(deck);
			
		for (int i = 0; i < Config.MAX_CLIENTS; i++)
		{
			distributeHands(deck, players[i]);
		}
		//We're going to have to do something like this elsewhere too for the next round
		//Tournament?
		
		game.setWinner(players[0]);
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
	}
	
	void setGameColour(String input) 
	{
		game.setColour(input);
		
	   	for (int i = 0; i < Config.MAX_CLIENTS; i++) 
		{
        	ServerThread thread = players[i].serverThread;//entity.getValue();

			logger.info(String.format("Asking players if they want to join tournament"));
	        thread.send(String.format("Would you like to join the tournament (Y or N)? Tournament colour is:%s\n", game.getColour()));
	        thread.send(String.format("Your hand is: \n"));
	        for(int k = 0; k < players[i].getHand().size(); k++)
			{
				thread.send(String.format("%s: %s card of value %d\n",players[i].getName(), players[i].getHand().get(k).getColour(), players[i].getHand().get(k).getValue()));
			}
		}
	   	
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
		
        for (int i = 0; i < Config.MAX_CLIENTS; i++) 
        {
        	j = 0;
        	ServerThread thread = players[i].serverThread;//entity.getValue();
        	if (players[i].getId() == id) {
		  		if (input.equalsIgnoreCase("Y"))
		  		{
		  			while (j < players[i].getHand().size()) {
		  				if (players[i].getHand().get(j).getColour().equals(game.getColour()) || players[i].getHand().get(j).getColour() == "white") 
		  				{
		  					if (players[i].equals(game.getWinner()))
		  						curPlayer = holder;
		  					Player player = new Player(players[i].getId(), players[i].serverThread);
		  					player.setName(players[i].getName());
		  					player.setHand(players[i].getHand());
		  					player.setNumPoints(0);
		  					tournamentPlayers[holder] = player;
					  		holder++;
			  				thread.send("You have successfully entered the tournament.\n");
							System.out.println("Player " + i + ": " + tournamentPlayers[i].getName());
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
        	tournament_1(tournamentPlayers[curPlayer]); //gameState = GameState.PROCESS_1;
        }
	}
	
	public void withdrawTournament(int id, String input)
	{
		if (input.equalsIgnoreCase("W"))
		{
			for (int i = 0; i < players.length; i++)	
			{
				if (players[i].getId() == id) {
					ServerThread thread = players[i].serverThread;
					thread.send("You will be removed from the tournament");
					//removes all cards from players hand and sets value of card in deck to 0
					for(int k = 0; k < deck.size(); k++)
					{
						for(int j = 0; j < players[i].getHand().size(); j++)
						{
							System.out.println("I: " + i);
							System.out.println("J: " + j);
							System.out.println("K: " + k);
							System.out.println("Player card: " + players[i].getHand().get(j).getID());
							System.out.println("Deck Card: " + deck.get(k).getID());
							
							if(players[i].getHand().get(j).getID() == deck.get(k).getID())
								deck.get(k).setState(0);
							players[i].getHand().remove(j);
						}
					}
					
					//call distributeHands
					distributeHands(deck, players[i]);
					int nextPlayer = 0;
					for (int j = 0; j < tournamentPlayers.length; j++) {
						if (players[i].getId() == tournamentPlayers[j].getId()) {
							removePlayer(tournamentPlayers[j]);
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
		Player[] result = new Player[numPlayers - 1];
        for (int i = 0; i < numPlayers; i++) 
        {
        	if (tournamentPlayers[i] != player)
        	{
	        	Player p = new Player(tournamentPlayers[i].getId(), tournamentPlayers[i].serverThread);
				p.setName(tournamentPlayers[i].getName());
				p.setHand(tournamentPlayers[i].getHand());
				p.setNumPoints(0);
	        	result[i] = p;
            }
        }
        tournamentPlayers = null; 
        tournamentPlayers = result.clone();
     return true;
     }
	
	public void tournament_1(Player p) {
			ServerThread thread = p.serverThread;
			
			if (tournamentPlayers.length >= 2)	//there are still 2 players in the tournament
			{
				//Player picks up a  card
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
					
				//Ask player which card(s) they want to play
				thread.send("Which card(s) do you want to play? Enter the IDs, separated by commas\n");
			}
			else 
			{
				game.setWinner(p);
				logger.info(String.format("Winner is asked to pick the colour of the game"));
				thread.send("You won the tournament!! What colour would you like the game to be?\n :");
				gameState = GameState.SET_GAME_COLOUR;
			}
			gameState = GameState.PROCESS_1;
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
		
		//if (holder == Config.MAX_CLIENTS)
		//{
			curPlayer++; 
			if (curPlayer == tournamentPlayers.length)
				curPlayer = 0;
			//tournament_1(tournamentPlayers[curPlayer]);
			tournament_3(tournamentPlayers[curPlayer]);
	//	}
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