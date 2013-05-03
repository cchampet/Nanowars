package tests;

import java.awt.geom.Point2D;

import junit.framework.TestCase;
import engine.Unit;

/**
 * This class is useful to test some stuff about Unit (engine unit).
 * @author Yuki
 *
 */
public class UnitTest extends TestCase{
	public void testDirection(){
		Unit unit1 = new Unit(20, new Point2D.Float(1, 1), new Point2D.Float(15, 25));
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
