package fatima;

import java.net.*;

import org.apache.log4j.Logger;

import java.io.*;

public class ServerThread extends Thread 
{
	static Logger logger = Logger.getLogger(ServerThread.class.getName());
	
	private int ID = -1;
	private Socket socket = null;
	private Server server = null;
	private BufferedReader streamIn = null;
	private BufferedWriter streamOut = null;
	
	private String clientAddress = null;;

	private boolean done = false;

	public ServerThread(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
		this.clientAddress = socket.getInetAddress().getHostAddress();
	}

	public int getID() {
		return this.ID;
	}

	public String getSocketAddress () {
		return clientAddress;
	}
	
	/** The server processes the messages and passes it to the client to send it */
	public void send(String msg) {
		try {
			streamOut.write(msg);
			streamOut.flush();
		} catch (IOException ioe) {
			logger.fatal(Formatter.exception("send(String)", ioe));
			server.remove(ID);
		}
	}

	/** 
	 * server thread that listens for incoming message from the client
	 * on the assigned port 
	 * */
	public void run() {
		logger.info(Formatter.format("run()", "Server Thread Running", ID)); 
		while (!done) {
			try {
				/** Received a message and pass to the server to handle */
				String input = streamIn.readLine();
				logger.info(String.format("processing input %s", input));
				
				if (!server.checkDisconnent(ID, input) && !server.checkShutdown(ID, input)) {
					if (Server.gameState == GameState.FINISHED)
						server.startNewGame(ID, input);
					else 
					if (Server.gameState == GameState.SET_GAME_COLOUR)
							server.setGameColour(input);
					else
						if (Server.gameState == GameState.JOIN_TOURNAMENT)
							server.joinTournament(ID, input);
					/*else
						if (Server.gameState == GameState.PROCESS_1)
							server.tournament_1(input);*/
					else
						if (Server.gameState == GameState.PROCESS_1)
							server.tournament_2(ID, input);
					else
						if (Server.gameState == GameState.PROCESS_2)
							server.withdrawTournament(ID, input);
					else
					if (Server.gameState == GameState.SETUP)
						server.setPlayersName(ID, input);
				}
			} catch (IOException ioe) {
				logger.fatal(Formatter.exception("run()", ioe));
				server.remove(ID);
				break;
			}}
	}

	public void open() throws IOException {
		streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));		
		logger.info(Formatter.format("open()", "Opened Socket Streams", socket.getInetAddress(), socket.getPort())); 
	}

	public void close() {
		try {
			if (socket != null) socket.close();
			if (streamIn != null) streamIn.close();
			
			this.done = true;
			this.socket = null;
			this.streamIn = null;
		} catch (IOException ioe) { 
			logger.fatal(Formatter.exception("close()", ioe));
		}
	}

}
