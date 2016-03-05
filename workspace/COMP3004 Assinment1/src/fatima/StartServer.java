package fatima;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


@SuppressWarnings("unused")
public class StartServer {
	
	private static Boolean done = Boolean.FALSE;
	private static Boolean started = Boolean.FALSE;

	private static Scanner sc = new Scanner(System.in);
	private static Server appServer = null;
	
	static Logger logger = Logger.getLogger(StartServer.class.getName());
	
	public static void main(String[] argv) {

		System.out.println("Starting server ...");

		{
			System.out.println("Please enter in the number of player: ");
			String nop = sc.nextLine();
			Config.MAX_CLIENTS = Integer.parseInt(nop);
			
			logger.info(String.format("Number of players set to :%d",Config.MAX_CLIENTS));	
		}

		do {
			System.out.println("startup | shutdown");
			String input = sc.nextLine();
			
			if (input.equalsIgnoreCase("STARTUP") || input.equalsIgnoreCase("START") && !started)
			{
				System.out.println("Starting server ...");
				appServer = new Server(Config.DEFAULT_PORT);
				//appServer.start();
				started = Boolean.TRUE;
				logger.info(String.format("Server Started %s:%d", Config.DEFAULT_HOST, Config.DEFAULT_PORT));	
			}
			
			if (input.equalsIgnoreCase("SHUTDOWN") && started)
			{
				System.out.println("Shutting server down ...");
				appServer.shutdown();
				started = Boolean.FALSE;
				done = Boolean.TRUE;
				logger.info(String.format("Server Shutdown %s:%d", Config.DEFAULT_HOST, Config.DEFAULT_PORT));			
			}	
			
		} while (!done);

		System.out.println("Exiting ...");
		System.exit(1);
	}
}
