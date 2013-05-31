package engine.tower;

import java.awt.geom.Point2D;
import java.util.LinkedHashSet;

import dispatcher.Dispatcher;
import engine.Base;
import engine.Element;
import engine.Unit;

/**
 * This class represents a tower for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Tower extends Element {
	private static final double STEP_FOR_NEXT_LVL = 20;
	private static final int  MAX_LVL = 4;
	
	private final Base associatedBase;
	protected int level;
	protected int vision;
	protected LinkedHashSet<Unit> unitsInVision;
	
	private boolean waitingForBuilding;
	
	public Tower(int posX, int posY) {
		super(posX, posY, 0);
		
		this.level = 0;
		this.vision = 0;
		this.waitingForBuilding = false;
		this.unitsInVision = new LinkedHashSet<Unit>();
		
		//define the associatedBase of the tower
		int idOfTheNearestBase = -1;
		for(Base base:Dispatcher.getEngine().getBases()){
			if(idOfTheNearestBase < 0) 
				idOfTheNearestBase = base.getId();
			else if(base.getCenter().distance(this.position.x, this.position.y) < Dispatcher.getEngine().getBase(idOfTheNearestBase).getCenter().distance(this.position.x, this.position.y))
				idOfTheNearestBase = base.getId();
		}
		this.associatedBase = Dispatcher.getEngine().getBase(idOfTheNearestBase);
	}
	
	public Tower(Tower other){
		super((int)other.getPosition().x, (int)other.getPosition().y, other.getNbAgents());
		this.associatedBase = other.getAssociatedBase();
		this.level = other.getLevel();
		this.vision = other.getVision();
		this.unitsInVision = other.getUnitsInVision();
	}
	
	public void levelUp() {
		this.level++;
		this.updateStats();
	}
	
	public void levelDown(){
		if(this.level >= 1){
			this.level--;
			this.updateStats();
		}
	}
	
	public void updateStats(){
		this.vision = 500 * this.level;
	}
	
	/**
	 * Add some agents to a tower
	 * @param nbAgentsOfUnitSent - Number of agents to add to Tower
	 * @return Number of agents to send back if the tower is full, else -1
	 */
	public int addNbAgents(double nbAgentsOfUnitSent){
		this.nbAgents += nbAgentsOfUnitSent;
		
		//Construction step of the tower
		if(this.isNotBuiltYet()){
			if(this.nbAgents >= this.getTowerCost()){
				this.waitingForBuilding = true;
				int agentsExcess = (int) (this.nbAgents - STEP_FOR_NEXT_LVL);
				this.nbAgents -= agentsExcess;
				return agentsExcess;
			}
		}else{
			//Leveling step
			while(this.nbAgents > (this.level+1)*Tower.STEP_FOR_NEXT_LVL && this.level < Tower.MAX_LVL){
				levelUp();
			}
			
			if(this.isLevelMax()){
				int agentsExcess = (int) (this.nbAgents - this.level*Tower.STEP_FOR_NEXT_LVL);
				this.nbAgents -= agentsExcess;
				return agentsExcess;
			}
		}
		return -1;
	}
	
	public void buildTower(){
		this.waitingForBuilding = false;
		this.levelUp();
	}
	
	public void destroyTower(){
		this.nbAgents = 0;
		this.waitingForBuilding = false;
	}
	
	public void action(){};
	
	/**
	 * Remove the dead unit in the tower list
	 * @param unitToRemove the unit to remove
	 */
	public void refreshTowerVisionList(Unit unitToRemove){
		if(this.unitsInVision.contains(unitToRemove)){
			this.unitsInVision.remove(unitToRemove);
		}
	}
	
	public void sendUnitBackToBase(int sendBackAgents, Unit unit) {
		unit.setNbAgents(sendBackAgents);
		Element tmpElt = unit.getStart();
		unit.setStart(unit.getGoal());
		unit.setGoal(tmpElt);
		unit.setDirectionToOpposite();
	}
	
	/**
	 * Abstract methode to add a Unit in Tower vision list
	 * @param unitToAdd the Unit to add
	 */
	public void addToUnitsInVision(Unit unitToAdd){}
	
	// GETTERS & SETTERS

	public Base getAssociatedBase() {
		return associatedBase;
	}

	public int getLevel() {
		return level;
	}

	public int getVision() {
		return vision;
	}
	
	/**
	 * Get the cost for build a tower
	 * @return cost
	 */
	private double getTowerCost(){
		return Tower.STEP_FOR_NEXT_LVL;
	}
	
	public Point2D.Float getCenter() {
		return new Point2D.Float(this.position.x + 12, this.position.y + 12);
	}

	public boolean isWaitingForBuilding() {
		return waitingForBuilding;
	}
	
	public boolean isLevelMax() {
		return this.level == Tower.MAX_LVL ? true : false;
	}
	
	public boolean isNotBuiltYet(){
		return this.level == 0 ? true : false;
	}
	
	public LinkedHashSet<Unit> getUnitsInVision() {
		return unitsInVision;
	}
}
