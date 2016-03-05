package fatima;

public class Game 
{
	
	Player winner;
	String colour;
	static String prevColour;
	
	
	public static String getPrevColour() {
		return prevColour;
	}
	
	public static void setPrevColour(String prevColour) {
		Game.prevColour = prevColour;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	public String getColour() {
		return colour;
	}
	
	public void setColour(String colour) {
		this.colour = colour;
	}
}
