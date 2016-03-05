package fatima;

	import java.net.*;
import java.io.*;

public class Client implements Runnable {
	private int ID = 0;
	private Socket socket            = null;
	private Thread thread            = null;
	private ClientThread   client    = null;
	private BufferedReader console   = null;
	private BufferedReader streamIn  = null;
	private BufferedWriter streamOut = null;
	
	public Client (String serverName, int serverPort) {  
		System.out.println(ID + ": Establishing connection. Please wait ...");

		try {  
			this.socket = new Socket(serverName, serverPort);
			this.ID = socket.getLocalPort();
	    	System.out.printf("%d : Connected to %s:%d\n",ID, socket.getInetAddress(),socket.getLocalPort());
	      this.start();
		} catch(UnknownHostException uhe) {  
			uhe.printStackTrace();
		} catch(IOException ioe) {  
			ioe.printStackTrace();
	   }
	}

   public void start() {  
	   try {
	   	console	= new BufferedReader(new InputStreamReader(System.in));
		   streamIn	= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		   if (thread == null) {  
		   	client = new ClientThread(this, socket);
		      thread = new Thread(this);                   
		      thread.start();
		   }
	   } catch (IOException ioe) {
      	ioe.printStackTrace();
	   }
   }

	public void run() { 
		System.out.println(ID + ": Client Started...");
		while (thread != null) {  
			try {  
				if (streamOut != null) {
					streamOut.flush();
					streamOut.write(console.readLine() + "\n");
				} else {
					System.out.println(ID + ": Stream Closed");
				}
         }
         catch(IOException ioe) {  
         	ioe.printStackTrace();
         	stop();
         }}
		System.out.println(ID + ": Client Stopped...");
   }

   public void handle (String msg) {
	   if (msg != null) {
   		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				System.out.println(ID + "Good bye. Press RETURN to exit ...");
				stop();
			} else {
				System.out.println(msg + "\n");
			}}
   }

   public Boolean stop() {  
      try { 
      	if (thread != null) thread = null;
    	  	if (console != null) console.close();
    	  	if (streamIn != null) streamIn.close();
    	  	if (streamOut != null) streamOut.close();

    	  	if (socket != null) socket.close();

    	  	this.socket = null;
    	  	this.console = null;
    	  	this.streamIn = null;
    	  	this.streamOut = null;    	  
      } catch(IOException ioe) {  
      	ioe.printStackTrace();
      	return Boolean.FALSE;
      }
      client.close();
      return Boolean.TRUE;
   }

   public Boolean isConnected () {
	   System.out.println(socket.getPort());
	   return socket.isConnected();
   }
}
