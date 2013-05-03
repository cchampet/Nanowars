package engine;

import java.util.ArrayList;

/**
 * 
 * Engine Class is the physic engine class which run the program.
 * Contain the main function and create the renderer and every useful elements.
 * 
 * @author Jijidici
 *
 */
public class Engine{
	/**
	 * Data of our game
	 */
	private static final ArrayList<Base> bases = new ArrayList<Base>();
	private static final ArrayList<Unit> units = new ArrayList<Unit>();
	
	/**
	 * Add a created base to the list of bases, contained by the Engine.
	 * @param newBase 
	 */
	public void addBase(Base newBase){
		bases.add(newBase);
	}
	
	/**
	 * Add a unit to the list of units, contained by the Engine.
	 * @param newBase 
	 */
	public void addUnit(Unit newUnit){
		units.add(newUnit);
	}
	
	/**
	 * This method is called every frame, in order to compute our stuff (bases, units...) regulary.
	 * @param renderer 
	 * @throws InterruptedException 
	 */
	public void doATurnGame(){
		//production of bases
		for(Base b:bases){
		    b.prodAgents();
		}
		
		//move units
		for(Unit unit:units){
			unit.move();
		}
	}
	
	// GETTERS & SETTERS
	
	/**
	 * Get the unit by an id.
	 * @param id
	 * @return unit
	 */
	public Unit getUnit(int id){
		for(Unit unit:units){
			if(unit.getId() == id){
				return unit;
			}
		}
		return null;
	}
}
