package fatima;

import java.util.ArrayList;

public class Player 
{
	  private String name;
	  private int id;
	  public ServerThread serverThread;
	  private int numPoints;
      private ArrayList<Card> hand;
      private RulesEngine rulesEngine;
      
      public Player (int id, ServerThread serverThread) {
  		this.id = id;  
  		this.serverThread = serverThread;
  	  }
  	  
  	  public Player() {	}
	  
  	  public String getName() { return name; }
  	  public void setName(String name) { this.name = name; }
	
  	  public int getId() { return id;	}
  	  public void setId(int id) { this.id = id; }
  	  
  	  public int getNumPoints() {	return numPoints; }
	  public void setNumPoints(int numPoints) { this.numPoints = numPoints; }

	  public ArrayList<Card> getHand() { return hand; }
	  public void setHand(ArrayList<Card> hand) { this.hand = hand; }
	  public void addHand(Card card) { hand.add(card);}
	  
     //this part is just here so the rest of the program will work
	  int roll;
	  String Attack;
	  String Defense;
	  int attackSpeed;
	  int defenseSpeed;
	  int numWounds = 0;
	  Player opponent;
	  
	  
	  int numokens;
	     public int getNumokens() {
			return numokens;
		}

		public void setNumokens(int numokens) {
			this.numokens = numokens;
		}

		
	  
	  
	public Player getOpponent() {
		return opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	public int getNumWounds() {
		return numWounds;
	}
	public void setNumWounds(int numWounds) {
		this.numWounds = numWounds;
	}
	
	
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}
	public String getAttack() {
		//return (rulesEngine.getAttack(Attack, roll));
		return Attack;
	}
	public void setAttack(String attack) {
		Attack = attack;
	}
	public String getDefense() {
		return Defense;
	}
	public void setDefense(String defense) {
		Defense = defense;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public int getDefenseSpeed() {
		return defenseSpeed;
	}
	public void setDefenseSpeed(int defenseSpeed) {
		this.defenseSpeed = defenseSpeed;
	}
	  
	// player to Attack | Attack Move | Attack Speed | Defense Move | Defense Speed
		public void setMove (Player opponent, String input) {
			String[] tokens = input.split(" ");
			
			this.opponent = opponent;
			this.Attack = tokens[1];
			this.attackSpeed = Integer.parseInt(tokens[2]);		
			this.Defense = tokens[3];
			this.defenseSpeed = Integer.parseInt(tokens[4]);
			
		}
		
		String result;
		public void setResult (String result) {
			this.result = result;
		}
		
		public void setWounded () {
			this.numWounds += 1;
		}
		
		public String getResult () {
			return String.format("%s attacked %s : won attack %s : number of wounds %d\n", name, opponent.getName(), result, numWounds );
		}

	  
}
