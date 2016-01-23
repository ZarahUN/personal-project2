
public class RulesEngine 
{
  Player player1;
  Player player2;
  public static void main(String[] args)
  {
	  
  }
  /*
   * gets the changes attack after the dice has been rolled
   * depends on the number on the rolled dice
   */
  public String getAttack(String attack, int roll)
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
  public boolean isWounded(String attack, String defense)
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
  
  //makes sure the total number of players playing the game 
  //is between 2-4
  public boolean checkNumPlayers(int total)
  {
    
	if(total < 2 || total > 4)
	{
	    return false;
	}
	else
	{
	  return true;
	}
  }
}
