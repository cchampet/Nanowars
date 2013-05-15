package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import playable.Player;

/**
 * 
 * Engine Class is the physic engine class.
 * 
 * @author Jijidici
 *
 */
public class Engine{
	/**
	 * Data of our game
	 */
	private static CopyOnWriteArrayList<Base> bases = new CopyOnWriteArrayList<Base>();
	private static CopyOnWriteArrayList<Unit> units = new CopyOnWriteArrayList<Unit>();
	private static CopyOnWriteArrayList<Tower> towers = new CopyOnWriteArrayList<Tower>();
	
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
	 * Add a created tower to the list of towers, contained by the Engine.
	 * @param newBase 
	 */
	public void addTower(Tower newTower){
		towers.add(newTower);
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
				unit.resolveAttack();
				idDeleted.add(unit.getId());
				//iterUnits.remove();
				//we can't make this action with a CopyOnWriteArrayList : we need to create an other list based on the first one.
				CopyOnWriteArrayList<Unit> tmpListOfUnits = new CopyOnWriteArrayList<Unit>();
				for(Unit u:units){
					if(!u.equals(unit))
						tmpListOfUnits.add(u);
				}
				units = tmpListOfUnits;
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
	
	/**
	 * Get the base by an id.
	 * @param id
	 * @return base
	 */
	public Base getBase(int id) {
		for(Base base:bases){
			if(base.getId() == id){
				return base;
			}
		}
		return null;
	}
	
	/**
	 * Get the tower by an id.
	 * @param id
	 * @return tower
	 */
	public Tower getTower(int id) {
		for(Tower tower:towers){
			if(tower.getId() == id){
				return tower;
			}
		}
		return null;
	}
	
	public CopyOnWriteArrayList<Base> getBases(){
		return bases;
	}

	public CopyOnWriteArrayList<Unit> getUnits(){
		return units;
	}

	public ArrayList<Base> getBasesOfAPlayer(Player owner) {
		ArrayList<Base> basesOfTheOwner = new ArrayList<Base>();
		for(Base b:bases){
			if(b.getOwner() == owner){
				basesOfTheOwner.add(b);
			}
		}
		return basesOfTheOwner;
	}
	
	public ArrayList<Base> getAdversaryBasesOfAPlayer(Player owner) {
		ArrayList<Base> adversaryBases = new ArrayList<Base>();
		for(Base b:bases){
			if(b.getOwner() != owner){
				adversaryBases.add(b);
			}
		}
		return adversaryBases;
	}
	
	public ArrayList<Unit> getUnitsOfAPlayer(Player owner) {
		ArrayList<Unit> unitsOfTheOwner = new ArrayList<Unit>();
		for(Unit u:units){
			if(u.getOwner() == owner){
				unitsOfTheOwner.add(u);
			}
		}
		return unitsOfTheOwner;
	}

	public ArrayList<Tower> getTowerAround(Base base) {
		ArrayList<Tower> towersAround = new ArrayList<Tower>();
		for(Tower t:towers){
			if(t.getAssociatedBase().equals(base))
				towersAround.add(t);
		}
		return towersAround;
	}
}
