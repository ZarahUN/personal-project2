package fatima;

public class Card {
	private int id;
	private String colour;
	private int value;
	private int state; // 0 = in deck, 1 = in player hand, 2 = in discard pile
	
	public Card (int i, String c, int v) {
		id = i;
		colour = c;
		value = v; 
		state = 0;
	}
	
	public void setState(int state) { this.state = state; }
	
	public int getID() { return id; }
	public String getColour() { return colour; }
	public int getValue() { return value; }
	public int getState() { return state; }
	
}