import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogicTest
{
  private RulesEngine rulesEngine;
  
  @Before
  public void setUp()
  {
	  rulesEngine = new RulesEngine();
  }
  
	@Test
	public void testWounded() 
	{
		//when attack is thrust
		boolean t1 = rulesEngine.isWounded("Thrust", "Charge");
        assertEquals(t1, true);
        
        boolean t2 = rulesEngine.isWounded("Thrust", "Dodge");
        assertEquals(t2, false);
        
        boolean t3 = rulesEngine.isWounded("Thrust", "Duck");
        assertEquals(t3, false);
        
        //when attack is swing
        boolean s1 = rulesEngine.isWounded("Swing", "Dodge");
        assertEquals(s1, true);
        
        boolean s2 = rulesEngine.isWounded("Swing", "Charge");
        assertEquals(s2, false);
        
        boolean s3 = rulesEngine.isWounded("Swing", "Duck");
        assertEquals(s3, false);
      
        //when attack is smash
        boolean m1 = rulesEngine.isWounded("Smash", "Duck");
        assertEquals(m1, true);
        
        boolean m2 = rulesEngine.isWounded("Smash", "Charge");
        assertEquals(m2, false);
        
        boolean m3 = rulesEngine.isWounded("Smash", "Dodge");
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

	@Test
	public void testRolls()
	{
		//testing thrust
		String t1 = rulesEngine.getAttack("Thrust", 1);
		assertEquals(t1, "Thrust");
		
		String t2 = rulesEngine.getAttack("Thrust", 2);
		assertEquals(t2, "Thrust");
		
		String t3 = rulesEngine.getAttack("Thrust", 3);
		assertEquals(t3, "Smash");
		
		String t4 = rulesEngine.getAttack("Thrust", 4);
		assertEquals(t4, "Smash");
		
		String t5 = rulesEngine.getAttack("Thrust", 5);
		assertEquals(t5, "Swing");
		
		String t6 = rulesEngine.getAttack("Thrust", 6);
		assertEquals(t6, "Swing");
		
		//testing swing
		String s1 = rulesEngine.getAttack("Swing", 1);
		assertEquals(s1, "Swing");
		
		String s2 = rulesEngine.getAttack("Swing", 2);
		assertEquals(s2, "Swing");
		
		String s3 = rulesEngine.getAttack("Swing", 3);
		assertEquals(s3, "Thrust");
		
		String s4 = rulesEngine.getAttack("Swing", 4);
		assertEquals(s4, "Thrust");
		
		String s5 = rulesEngine.getAttack("Swing", 5);
		assertEquals(s5, "Smash");
		
		String s6 = rulesEngine.getAttack("Swing", 6);
		assertEquals(s6, "Smash");
		
		//testing smash
		String m1 = rulesEngine.getAttack("Smash", 1);
		assertEquals(m1, "Smash");
		
		String m2 = rulesEngine.getAttack("Smash", 2);
		assertEquals(m2, "Smash");
		
		String m3 = rulesEngine.getAttack("Smash", 3);
		assertEquals(m3, "Swing");
		
		String m4 = rulesEngine.getAttack("Smash", 4);
		assertEquals(m4, "Swing");
		
		String m5 = rulesEngine.getAttack("Smash", 5);
		assertEquals(m5, "Thrust");
		
		String m6 = rulesEngine.getAttack("Smash", 6);
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
	
	
}
