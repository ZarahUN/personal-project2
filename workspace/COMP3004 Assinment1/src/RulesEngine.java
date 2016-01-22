
public class RulesEngine 
{
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
	 if(attack.equalsIgnoreCase("Thrust") && defense.equalsIgnoreCase("charge"))
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
  
  public boolean checkSpeed(int P1, int P2)
  {
	  if(P1 > P2)
	  {
		  return true;
	  }
	  else 
	  {
		  return false;
	  }
  }
}
