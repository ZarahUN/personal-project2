package filters;

public final class Filter {
	private static final char SPACE = ' ';
	private static final String httpregex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	public static String process (String input) {
		if (input.length() == 0) return null;
		try 
		{
			// 1: Replace any https: references
			// 2: Remove all not alpha characters except the hash tags		
			input = input.replaceAll(httpregex, "");
			input = input.replaceAll("[^a-zA-Z #]", "").toLowerCase();
			input = input.replaceAll("null", "").toLowerCase();
			
			while (input.charAt(0) ==  SPACE) 
				input = input.replaceFirst(" ", "");

			while (input.contains("  "))
				input = input.replaceAll("  ", " ").toLowerCase();
			
			return input;		
		} catch (Exception e) {
			return null;
		}
	}
}
