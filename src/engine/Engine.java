package engine;

import java.util.ArrayList;
import java.util.Iterator;

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
	 * @return an ArrayList<Integer> which contains all id of engine elements just deleted.
	 */
	public ArrayList<Integer> doATurnGame(){
		ArrayList<Integer> idDeleted = new ArrayList<Integer>();
		
		//production of bases
		for(Base b:bases){
		    b.prodAgents();
		}
		
		//move units
		for(Unit unit:units){
			unit.move();
		}
		
		//stop movment of concerned units
		Iterator<Unit> iterUnits = units.iterator();
		while(iterUnits.hasNext()){
			Unit unit = iterUnits.next();
			if(unit.atDestination()){
				idDeleted.add(unit.getId());
				iterUnits.remove();
			}
		}
		
		return idDeleted;
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
