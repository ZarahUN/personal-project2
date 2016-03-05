package fatima;

import java.net.InetAddress;
import java.net.Socket;

public final class Formatter {

	public static String exception (Exception e) {
		return e.getMessage();
	}

	public static Object format(String string, Socket socket) {
		return String.format("Client accepted %s:%d", socket.getInetAddress().toString(), socket.getPort());
	}
	
	public static String exception (String method, Exception e) {
		return String.format("Exception Thrown: Method %s Exception %s",method, e.getMessage());
	}


	public static Object format(String message, int iParam) {
		return String.format("%s : %d", message, iParam ); 
	}

	public static String format (String method, String message, int iParam) {  
		return String.format("%s : %s : %d", method, message, iParam ); 
	}
	
	public static String format (String method, String message, InetAddress inet, int iParam) {  
		return String.format("%s : %s : %s:%d", method, message, inet.toString(), iParam ); 
	}

	public static String formatChatMessage (ServerThread from, ServerThread to, String message) {
		return
		String.format( "Sending Message from %s:%d to %s:%d: %s ", from.getSocketAddress(),from.getID(), to.getSocketAddress(), to.getID(), message);
	}

	public static String getDefaultErrorMessage () {
		return
		String.format("%s\n%s\n","This is an animal free chat service","Please refrain from referring to any form of animals");
	}

}
