package tests;

import java.awt.geom.Point2D;

import junit.framework.TestCase;
import engine.Base;
import engine.Unit;

/**
 * This class is useful to test some stuff about Unit (engine unit).
 * @author Yuki
 *
 */
public class UnitTest extends TestCase{
	public void testDirection(){
		Base newBase = new Base(0, 0, 50);
		Unit unit1 = new Unit(20, new Point2D.Float(1, 1), new Point2D.Float(15, 25), newBase);
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
