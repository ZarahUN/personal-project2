package fatima;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogicTest
{
  private RulesEngine rulesEngine;

  Player player1;
  Player player2;
  Player player3;
  Player player4;
  
  
  
  @Before
  public void setUp()
  {
	  rulesEngine = new RulesEngine();
	  
	  player1 = new Player();
	  player2 = new Player();
	  player3 = new Player();
	  player4 = new Player();
	  
	  player1.setAttack("Thrust");
	  player2.setAttack("Swing");
	  player3.setAttack("Smash");
	  player4.setAttack("Thrust");
	  
	  player1.setDefense("Charge");
	  player2.setDefense("Dodge");
	  player3.setDefense("Duck");
	  player4.setDefense("Charge");
	  
	  player1.setAttackSpeed(1);
	  player2.setAttackSpeed(2);
	  player3.setAttackSpeed(3);
	  player4.setAttackSpeed(1);
	  
	  player1.setDefenseSpeed(1);
	  player2.setDefenseSpeed(2);
	  player3.setDefenseSpeed(3);
	  player4.setDefenseSpeed(1);
	  
	  
  }
  
	@Test
	public void testWounded() 
	{
		//when attack is thrust
		boolean t1 = RulesEngine.isWounded("Thrust", "Charge");
        assertEquals(t1, true);
        
        boolean t2 = RulesEngine.isWounded("Thrust", "Dodge");
        assertEquals(t2, false);
        
        boolean t3 = RulesEngine.isWounded("Thrust", "Duck");
        assertEquals(t3, false);
        
        //when attack is swing
        boolean s1 = RulesEngine.isWounded("Swing", "Dodge");
        assertEquals(s1, true);
        
        boolean s2 = RulesEngine.isWounded("Swing", "Charge");
        assertEquals(s2, false);
        
        boolean s3 = RulesEngine.isWounded("Swing", "Duck");
        assertEquals(s3, false);
      
        //when attack is smash
        boolean m1 = RulesEngine.isWounded("Smash", "Duck");
        assertEquals(m1, true);
        
        boolean m2 = RulesEngine.isWounded("Smash", "Charge");
        assertEquals(m2, false);
        
        boolean m3 = RulesEngine.isWounded("Smash", "Dodge");
        assertEquals(m3, false);
        
        boolean c1 = rulesEngine.checkSpeed(1,2);
        assertEquals(c1, false);
        
        boolean c2 = rulesEngine.checkSpeed(1,3);
        assertEquals(c2, false);
        
        boolean c3 = rulesEngine.checkSpeed(2,1);
        assertEquals(c3, true);
        
        boolean c4 = rulesEngine.checkSpeed(2,3);
        assertEquals(c4, false);
        
        boolean c5 = rulesEngine.checkSpeed(3,1);
        assertEquals(c5, true);
        
        boolean c6 = rulesEngine.checkSpeed(3,2);
        assertEquals(c6, true);
	}

	/*@Test
	public void testRolls01 () {
		
	}*/
	
	@Test
	public void testRolls()
	{
		//testing thrust
		String t1 = RulesEngine.getAttack("Thrust", 1);
		assertEquals(t1, "Thrust");
		
		String t2 = RulesEngine.getAttack("Thrust", 2);
		assertEquals(t2, "Thrust");
		
		String t3 = RulesEngine.getAttack("Thrust", 3);
		assertEquals(t3, "Smash");
		
		String t4 = RulesEngine.getAttack("Thrust", 4);
		assertEquals(t4, "Smash");
		
		String t5 = RulesEngine.getAttack("Thrust", 5);
		assertEquals(t5, "Swing");
		
		String t6 = RulesEngine.getAttack("Thrust", 6);
		assertEquals(t6, "Swing");
		
		//testing swing
		String s1 = RulesEngine.getAttack("Swing", 1);
		assertEquals(s1, "Swing");
		
		String s2 = RulesEngine.getAttack("Swing", 2);
		assertEquals(s2, "Swing");
		
		String s3 = RulesEngine.getAttack("Swing", 3);
		assertEquals(s3, "Thrust");
		
		String s4 = RulesEngine.getAttack("Swing", 4);
		assertEquals(s4, "Thrust");
		
		String s5 = RulesEngine.getAttack("Swing", 5);
		assertEquals(s5, "Smash");
		
		String s6 = RulesEngine.getAttack("Swing", 6);
		assertEquals(s6, "Smash");
		
		//testing smash
		String m1 = RulesEngine.getAttack("Smash", 1);
		assertEquals(m1, "Smash");
		
		String m2 = RulesEngine.getAttack("Smash", 2);
		assertEquals(m2, "Smash");
		
		String m3 = RulesEngine.getAttack("Smash", 3);
		assertEquals(m3, "Swing");
		
		String m4 = RulesEngine.getAttack("Smash", 4);
		assertEquals(m4, "Swing");
		
		String m5 = RulesEngine.getAttack("Smash", 5);
		assertEquals(m5, "Thrust");
		
		String m6 = RulesEngine.getAttack("Smash", 6);
		assertEquals(m6, "Thrust");
	}
	
	@Test
	public void testTotal()
	{
		boolean r1 = rulesEngine.checkTotalNum(1, 1);
		assertEquals(r1, false);
		
		boolean r2 = rulesEngine.checkTotalNum(1, 2);
		assertEquals(r2, false);
		
		boolean r3 = rulesEngine.checkTotalNum(1, 3);
		assertEquals(r3, true);
		
		boolean r4 = rulesEngine.checkTotalNum(2, 1);
		assertEquals(r4, false);
		
		boolean r5 = rulesEngine.checkTotalNum(2, 2);
		assertEquals(r5, true);
		
		boolean r6 = rulesEngine.checkTotalNum(2, 3);
		assertEquals(r6, true);
		
		boolean r7 = rulesEngine.checkTotalNum(3, 1);
		assertEquals(r7, true);
		
		boolean r8 = rulesEngine.checkTotalNum(3, 2);
		assertEquals(r8, true);
		
		boolean r9 = rulesEngine.checkTotalNum(3, 3);
		assertEquals(r9, true);
	}
	
	@Test
	public void test2PlayerAttack()
	{
		//player1 attacking
		boolean r1 = RulesEngine.isWounded(player1.getAttack(), player2.getDefense());
		assertEquals(r1, false);
		
		//player2 attacking
		boolean r2 = RulesEngine.isWounded(player2.getAttack(), player1.getDefense());
		assertEquals(r2, false);
	}
	
	@Test
	public void test2PlayerAttackSpeed()
	{
		//player1 attacking
		boolean r1 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r1, false);
		
		//player2 attacking
		boolean r2 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r2, true);
	}
	
	@Test
	public void test3PlayerAttack()
	{
		//player1 attacking
		boolean r1 = RulesEngine.isWounded(player1.getAttack(), player2.getDefense());
		assertEquals(r1, false);
		
		boolean r2 = RulesEngine.isWounded(player1.getAttack(), player3.getDefense());
		assertEquals(r2, false);
		
		//player2 attacking
		boolean r3 = RulesEngine.isWounded(player2.getAttack(), player1.getDefense());
		assertEquals(r3, false);
		
		boolean r4 = RulesEngine.isWounded(player2.getAttack(), player3.getDefense());
		assertEquals(r4, false);
		
		//player3 attacking
		boolean r5 = RulesEngine.isWounded(player3.getAttack(), player1.getDefense());
		assertEquals(r5, false);
		
		boolean r6 = RulesEngine.isWounded(player3.getAttack(), player2.getDefense());
		assertEquals(r6, false);
	}
	
	@Test
	public void test3PlayerAttackSpeed()
	{
		//player1 attacking
		boolean r1 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r1, false);
		
		boolean r2 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player3.getDefenseSpeed());
		assertEquals(r2, false);
		
		//player2 attacking
		boolean r3 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r3, true);
		
		boolean r4 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player3.getDefenseSpeed());
		assertEquals(r4, false);
		
		//player3 attacking
		boolean r5 = rulesEngine.checkSpeed(player3.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r5, true);
		
		boolean r6 = rulesEngine.checkSpeed(player3.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r6, true);
	}
	
	@Test
	public void test4PlayerAttack()
	{
		//player1 attacking
		boolean r1 = RulesEngine.isWounded(player1.getAttack(), player2.getDefense());
		assertEquals(r1, false);
		
		boolean r2 = RulesEngine.isWounded(player1.getAttack(), player3.getDefense());
		assertEquals(r2, false);
		
		boolean r3 = RulesEngine.isWounded(player1.getAttack(), player4.getDefense());
		assertEquals(r3, true);
		
		//player2 attacking
		boolean r4 = RulesEngine.isWounded(player2.getAttack(), player1.getDefense());
		assertEquals(r4, false);
		
		boolean r5 = RulesEngine.isWounded(player2.getAttack(), player3.getDefense());
		assertEquals(r5, false);
		
		boolean r6 = RulesEngine.isWounded(player2.getAttack(), player4.getDefense());
		assertEquals(r6, false);
		
		//player3 attacking
		boolean r7 = RulesEngine.isWounded(player3.getAttack(), player1.getDefense());
		assertEquals(r7, false);
		
		boolean r8 = RulesEngine.isWounded(player3.getAttack(), player2.getDefense());
		assertEquals(r8, false);
		
		boolean r9 = RulesEngine.isWounded(player3.getAttack(), player4.getDefense());
		assertEquals(r9, false);
		
		//player4 attacking
		boolean r10 = RulesEngine.isWounded(player4.getAttack(), player1.getDefense());
		assertEquals(r10, true);
		
		boolean r11 = RulesEngine.isWounded(player4.getAttack(), player2.getDefense());
		assertEquals(r11, false);
		
		boolean r12 = RulesEngine.isWounded(player4.getAttack(), player3.getDefense());
		assertEquals(r12, false);
	}
	
	@Test
	public void test4PlayerAttackSpeed()
	{
		//player1 attacking
		boolean r1 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r1, false);
		
		boolean r2 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player3.getDefenseSpeed());
		assertEquals(r2, false);
		
		boolean r3 = rulesEngine.checkSpeed(player1.getAttackSpeed(), player4.getDefenseSpeed());
		assertEquals(r3, false);
		
		//player2 attacking
		boolean r4 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r4, true);
		
		boolean r5 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player3.getDefenseSpeed());
		assertEquals(r5, false);
		
		boolean r6 = rulesEngine.checkSpeed(player2.getAttackSpeed(), player4.getDefenseSpeed());
		assertEquals(r6, true);
		
		//player3 attacking
		boolean r7 = rulesEngine.checkSpeed(player3.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r7, true);
		
		boolean r8 = rulesEngine.checkSpeed(player3.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r8, true);
		
		boolean r9 = rulesEngine.checkSpeed(player3.getAttackSpeed(), player4.getDefenseSpeed());
		assertEquals(r9, true);
		
		//player4 attacking
		boolean r10 = rulesEngine.checkSpeed(player4.getAttackSpeed(), player1.getDefenseSpeed());
		assertEquals(r10, false);
		
		boolean r11 = rulesEngine.checkSpeed(player4.getAttackSpeed(), player2.getDefenseSpeed());
		assertEquals(r11, false);
		
		boolean r12 = rulesEngine.checkSpeed(player4.getAttackSpeed(), player3.getDefenseSpeed());
		assertEquals(r12, false);
	}
}
