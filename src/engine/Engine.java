package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;

import engine.tower.Tower;
import engine.tower.TowerDamage;
import engine.tower.TowerDivision;
import engine.tower.TowerFreeze;
import engine.tower.TowerPoison;
import engine.tower.TowerProliferation;
import engine.tower.TowerResistant;
import engine.tower.TowerSpeed;
import engine.tower.TowerZone;

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

		//launch action of towers
		for(Tower tower:Engine.towers){
			//no action on neutral tower
			if(tower.getAssociatedBase().getOwner() != null){
				for(Unit unit:Engine.units){
					if(tower.distanceToElement(unit)<=tower.getVision()){
						tower.addToUnitsInVision(unit);
					}
				}
				tower.action();
			}
		}
		
		//move units
		for(Unit unit:units){
			if(unit.isDivided()){
				divideUnit(unit);
			}else{
				unit.move();
			}
		}
		
		//stop movement of concerned units
		Iterator<Unit> iterUnits = units.iterator();
		while(iterUnits.hasNext()){
			Unit unit = iterUnits.next();
			if(unit.atDestination() || unit.getNbAgents()<=0){
				if(unit.resolveAttack()){
					idDeleted.add(unit.getId());
					//we can't make this action with a CopyOnWriteArrayList : we need to create an other list based on the first one.
					CopyOnWriteArrayList<Unit> tmpListOfUnits = new CopyOnWriteArrayList<Unit>();
					for(Unit u:units){
						if(!u.equals(unit))
							tmpListOfUnits.add(u);
					}
					units = tmpListOfUnits;
					unit.setAliveFlag(false);
				}
				
				//Remove dead units in tower list
				for(Tower tower: Engine.towers){
					tower.refreshTowerVisionList(unit);
				}
			}
		}
		return idDeleted;
	}
	
	/**
	 * Specialize the given tower by a tower of the given type. Replace it in the engine's tower list
	 * @param type
	 * @param towerToSpecialize
	 * @return new specialized Tower id to update the corresponding TowerSprite
	 */
	public Tower specializeTower(TypeOfTower type, Tower towerToSpecialize){
		Tower specializedTower = null;
		try{
			specializedTower = constructChosenTowerFromSimpleTower(type, towerToSpecialize);
			specializedTower.buildTower();
		}catch(RuntimeException e){
			e.printStackTrace();
			System.exit(0);
		}
		
		//replace the tower in tower list
		CopyOnWriteArrayList<Tower> tmpListOfTowers = new CopyOnWriteArrayList<Tower>();
		for(Tower tower:Engine.towers){
			if(tower.equals(towerToSpecialize)){
				tmpListOfTowers.add(specializedTower);
			}else{
				tmpListOfTowers.add(tower);
			}
		}
		Engine.towers = tmpListOfTowers;
		return specializedTower;
	}
	
	/**
	 * Unspecialize a tower by building a no-type tower
	 * @param towerToUnspecialize
	 * @return basic tower
	 */
	public Tower unspecializeTower(Tower towerToUnspecialize){
		Tower newTower = specializeTower(TypeOfTower.NONE, towerToUnspecialize);
		newTower.destroyTower();
		while(newTower.getLevel() > 0){
			newTower.levelDown();
		}
		return newTower;
	}
	
	/**
	 * Construct chosen specialized Tower from a generic one
	 * @param type Type of the tower to construct
	 * @param other The generic Tower
	 * @return the chosen tower
	 */
	private Tower constructChosenTowerFromSimpleTower(TypeOfTower type, Tower other){
		Tower newTower = null;
		switch(type){
			case NONE:
				newTower = new Tower(other);
				break;		
			case DAMAGE:
				newTower = new TowerDamage(other);
				break;
			case POISON:
				newTower = new TowerPoison(other);
				break;
			case FREEZE:
				newTower = new TowerFreeze(other);
				break;
			case ZONE:
				newTower = new TowerZone(other);
				break;
			case DIVISION:
				newTower = new TowerDivision(other);
				break;
			case PROLIFERATION:
				newTower = new TowerProliferation(other);
				break;
			case RESISTANT:
				newTower = new TowerResistant(other);
				break;
			case SPEED:
				newTower = new TowerSpeed(other);
				break;
			default:
				throw new RuntimeException("Unvalid TypeOfTower during Tower specialization");
		}
		return newTower;
	}
	
	/**
	 * In case of damage DIVISION Unit, split it in two little unit
	 * @param motherUnit the unit to divide
	 */
	public void divideUnit(Unit motherUnit){
		Dispatcher.getRenderer().removeSprite(motherUnit.getId());
		//remove mother unit in tower vision list
		for(Tower tower:Engine.towers){
			tower.refreshTowerVisionList(motherUnit);
		}
		
		//compute orthogonal vector to the mother unit direction
		Point2D.Float orthDirection = new Point2D.Float();
		if(motherUnit.getDirection().x == 0){
			orthDirection.x = 1;
			orthDirection.y = 0;
		}else{
			orthDirection.x = 1;
			orthDirection.y = - (motherUnit.getDirection().x/motherUnit.getDirection().y);
			double orthNorm = Math.sqrt(1 + orthDirection.y*orthDirection.y);
			orthDirection.x /= orthNorm;
			orthDirection.y /= orthNorm;			
		}
		
		//create children unit
		Unit childUnit1 = new Unit(motherUnit.getNbAgents()/2, (Base)motherUnit.getStart(), motherUnit.getGoal());
		Unit childUnit2 = new Unit(motherUnit.getNbAgents()/2, (Base)motherUnit.getStart(), motherUnit.getGoal());
		
		Point2D.Float child1Position = new Point2D.Float(motherUnit.getPosition().x + (10+childUnit1.getNbAgents()/4)*orthDirection.x,
														 motherUnit.getPosition().y + (10+childUnit1.getNbAgents()/4)*orthDirection.y);
		childUnit1.setPosition(child1Position);
		Point2D.Float child2Position = new Point2D.Float(motherUnit.getPosition().x - (10+childUnit1.getNbAgents()/4)*orthDirection.x,
				 										 motherUnit.getPosition().y - (10+childUnit1.getNbAgents()/4)*orthDirection.y);
		childUnit2.setPosition(child2Position);
		
		//add to collections in engine and renderer
		childUnit1.setId(Dispatcher.getRenderer().addUnitSprite(childUnit1));
		childUnit2.setId(Dispatcher.getRenderer().addUnitSprite(childUnit2));
		
		CopyOnWriteArrayList<Unit> tmpListOfUnits = new CopyOnWriteArrayList<Unit>();
		for(Unit u:units){
			if(u != motherUnit)
				tmpListOfUnits.add(u);
		}
		tmpListOfUnits.add(childUnit1);
		tmpListOfUnits.add(childUnit2);
		units = tmpListOfUnits;
		
	}
	
	// GETTERS & SETTERS
	
	public CopyOnWriteArrayList<Base> getBases(){
		return bases;
	}

	public CopyOnWriteArrayList<Unit> getUnits(){
		return units;
	}
	
	public static CopyOnWriteArrayList<Tower> getTowers() {
		return towers;
	}
	
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
	
	public ArrayList<Tower> getTowersOfAPlayer(Player owner) {
		ArrayList<Tower> towersOfTheOwner = new ArrayList<Tower>();
		for(Tower t:towers){
			if(t.getAssociatedBase().getOwner() == owner){
				towersOfTheOwner.add(t);
			}
		}
		return towersOfTheOwner;
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
