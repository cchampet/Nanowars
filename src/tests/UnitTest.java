package tests;

import javax.vecmath.Vector2f;

import engine.Unit;
import junit.framework.TestCase;

/**
 * This class is useful to test some stuff about Unit (engine unit).
 * @author Yuki
 *
 */
public class UnitTest extends TestCase{
	public void testDirection(){
		Unit unit1 = new Unit(20, new Vector2f(1, 1), new Vector2f(15.5f, 25.5f));
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
