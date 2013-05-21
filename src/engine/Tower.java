package engine;

import java.awt.geom.Point2D;

import dispatcher.Dispatcher;

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
	
	private boolean waitingForBuilding;
	
	public Tower(int posX, int posY) {
		super(posX, posY, 0);
		
		this.level = 0;
		this.vision = 0;
		this.waitingForBuilding = false;
		
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
	
	public void levelUp() {
		this.level++;
		this.vision = 20 * this.level;
	}
	
	/**
	 * Add some agents to a tower
	 * @param nbAgentsOfUnitSent - Number of agents to add to Tower
	 * @return Number of agents to send back if the tower is full, else -1
	 */
	public int addNbAgents(double nbAgentsOfUnitSent){
		this.nbAgents += nbAgentsOfUnitSent;
		while(this.nbAgents > (this.level+1)*Tower.STEP_FOR_NEXT_LVL && this.level < Tower.MAX_LVL){
			if(this.isNotBuiltYet())
				this.waitingForBuilding = true;
			levelUp();
		}
		
		if(this.isLevelMax()){
			int agentsExcess = (int) (this.nbAgents - this.level*Tower.STEP_FOR_NEXT_LVL);
			this.nbAgents -= agentsExcess;
			return agentsExcess;
		}
		return -1;
	}
	
	public void destroyTower(){
		this.nbAgents = 0;
		this.level = 0;
	}

	public Unit sendUnit(double nbAgentsOfUnitSent, Element endingElement) {
		Unit newUnit = new Unit(nbAgentsOfUnitSent, this.getAssociatedBase(), endingElement);
		newUnit.setId(Dispatcher.getRenderer().addUnitSprite(newUnit));
		Dispatcher.getEngine().addUnit(newUnit);
		return newUnit;
	}
	

	public void sendUnitBackToBase(int sendBackAgents, Unit unit) {
		unit.setNbAgents(sendBackAgents);
		Element tmpElt = unit.getStart();
		unit.setStart(unit.getGoal());
		unit.setGoal(tmpElt);
		unit.setDirectionToOpposite();
	}
	
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
}
