package tests;

import junit.framework.TestCase;
import playable.Player;
import playable.TypeOfPlayer;
import engine.Base;
import engine.Unit;

/**
 * This class is useful to test some stuff about Unit (engine unit).
 * @author Yuki
 *
 */
public class UnitTest extends TestCase{
	public void testDirection(){
		Player newPlayer = new Player("UTest", TypeOfPlayer.PLAYER);
		Base newBase1 = new Base(0, 0, 50, newPlayer);
		Base newBase2 = new Base(10, 20, 100, newPlayer);
		Unit unit1 = new Unit(20,newBase1, newBase2);
		for(int i = 0; i<50; i++){
			System.out.println("position : "+unit1.position.x + ", " + unit1.position.y);
			unit1.move();
			if (unit1.atDestination()) {
				assertTrue(true);
				return;
			}
			assertFalse(false);
		}
	}
}
