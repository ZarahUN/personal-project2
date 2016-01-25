package fatima;

public class RulesEngine 
{
	 public static void main(String[] args){}
  /*
   * gets the changes attack after the dice has been rolled
   * depends on the number on the rolled dice
   */
  public static String getAttack(String attack, int roll)
  {
	  if(roll == 1 || roll == 2)
	  {
		  return attack;
	  }
	  else if(roll == 3 || roll == 4 )
	  {
		  if(attack == "Thrust")
		  {
			  attack = "Smash";
		  }
		  else if(attack == "Swing")
		  {
			  attack = "Thrust";
		  }
		  else if(attack == "Smash")
		  {
			  attack = "Swing";
		  }
		  
	  }
	  else if(roll == 5 || roll == 6 )
	  {
	      if(attack == "Thrust")
		  {
			  attack = "Swing";
		  }
	      else if(attack == "Swing")
		  {
			  attack = "Smash";
		  }
	      else if(attack == "Smash")
		  {
			  attack = "Thrust";
		  }
		  
	  }
	  return attack;
  }
	//this function determines if a player is wounded after a round
  public static boolean isWounded(String attack, String defense)
  {
	 if(attack.equalsIgnoreCase("Thrust") && defense.equalsIgnoreCase("Charge"))
	 {
		 return true;
	 }
	 else if(attack == "Swing" && defense == "Dodge")
	 {
		 return true;
	 }
	 else if(attack == "Smash" && defense == "Duck")
	 {
		 return true;
	 }
	 else
	 {
	     return false;
	 }
  }
  //function to determine if player is wounded based on speed
  public boolean checkSpeed(int P1attack, int P2defense)
  {
	  if(P1attack > P2defense)
	  {
		  return true;
	  }
	  else 
	  {
		  return false;
	  }
  }
  
  //makes sure the total number of attack and defense is < 4
  public boolean checkTotalNum(int attackNum, int defenseNum)
  {
    int total = attackNum + defenseNum;
    
    if(total < 4)
    {
    	return false;
    }
    else
    {
    	return true;
    }
  }
  
  //checking connection of clients to server
  public boolean checkConnection(Player[] players)
  {
	  int counter= 0;
	  for(Player p : players)
	  {
		  if(p.getId() != 0)
		  {
			counter++;
		  }
	  }
	  if(counter == Config.MAX_CLIENTS)
	  {
		  return true;
	  }
	  else
	  {
		  return false;
	  }
  }
  
  //processes the attack for each player
  public static void process (Player[] players) {
		for (Player p : players) {
			processAttack (p, p.getOpponent());
		}
	}
	
  //calls the isWounded function to process the attack and sets the result of the round for each player
	private static Boolean processAttack ( Player attacker, Player defender) {
		isWounded(attacker.getAttack(), defender.getDefense());
		attacker.setResult("");
		defender.setResult("");
		return true;
	}

}
