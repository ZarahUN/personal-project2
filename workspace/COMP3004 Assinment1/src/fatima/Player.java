package fatima;

public class Player 
{
	  String name;
	  int id;
	  int roll;
	  String Attack;
	  String Defense;
	  int attackSpeed;
	  int defenseSpeed;
	  int numWounds = 0;
	  Player opponent;
	  
	  Player (int id) {
		this.id = id;  
	  }
	  
	  public Player() {
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
	RulesEngine rulesEngine;
	  
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
