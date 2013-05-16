package engine;

import java.awt.geom.Point2D;

import dispatcher.Dispatcher;

/**
 * This class represents a tower for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Tower extends Element {
	private static final double INIT_NB_AGENTS = 20;
	private final Base associatedBase;
	protected int level;
	protected int vision;
	
	public Tower(int posX, int posY) {
		super(posX, posY, INIT_NB_AGENTS);
		
		this.level = 0;
		this.vision = 0;
		
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
		this.nbAgents = 20 * this.level;
		this.vision = 20 * this.level;
	}
	
	public void addNbAgents(double nbAgentsOfUnitSent){
		if(nbAgentsOfUnitSent == this.nbAgents)
			this.levelUp();
		else if(nbAgentsOfUnitSent < this.nbAgents)
			this.nbAgents -= nbAgentsOfUnitSent;
		else{
			nbAgentsOfUnitSent -= this.nbAgents;
			this.levelUp();
			this.nbAgents -= nbAgentsOfUnitSent;
		}
	}
	
	public void destroyTower(){
		this.nbAgents = INIT_NB_AGENTS;
		this.level = 0;
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
}
