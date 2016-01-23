
public class Player 
{
	  String name;
	  int id;
	  int roll;
	  String Attack;
	  String Defense;
	  int attackSpeed;
	  int defenseSpeed;
	  int numWounds;
	  
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
	  
	  
	  
}
