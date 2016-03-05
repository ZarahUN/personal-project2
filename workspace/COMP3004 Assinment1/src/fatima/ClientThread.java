package fatima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket         socket   = null;
	private Client      client   = null;
	private BufferedReader streamIn = null;
	private boolean done = false;
	
	public ClientThread(Client client, Socket socket) {  
		this.client = client;
		this.socket = socket;
		this.open();  
		this.start();
	}
	
	public void open () {
		try {  
			streamIn  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    } catch(IOException ioe) {  
	   	 //ioe.printStackTrace();
	   	 client.stop();
	    }
	}
	
	public void close () {
		done = true;
		try {  
			if (streamIn != null) streamIn.close();
			if (socket != null) socket.close();
			this.socket = null;
			this.streamIn = null;
		} catch(IOException ioe) { 
			ioe.printStackTrace();
	   }	
	}
	
	public void run() {
		System.err.println("Client Thread " + socket.getLocalPort() + " running.");
		while (!done) { 
			try {  
				client.handle(streamIn.readLine());
			} catch(IOException ioe) {  
				//ioe.printStackTrace();
	    }}
	}	

}
