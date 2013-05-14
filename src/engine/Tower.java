package engine;

import java.awt.geom.Point2D;

import dispatcher.Dispatcher;

public abstract class Tower {
	private int id;
	private final Point2D.Float position;
	private final Base associatedBase;
	protected double nbAgents;
	protected double nbAgentsToNextLevel;
	protected int level;
	protected int vision;
	
	public Tower(int posX, int posY) {
		this.position = new Point2D.Float(posX, posY);
		this.level = 1;
		this.nbAgents = 20;
		this.nbAgentsToNextLevel = 20;
		this.vision = 20;
		
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
		this.nbAgentsToNextLevel = 20 * this.level;
		this.vision = 20 * this.level;
	}

	// GETTERS & SETTERS
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbAgents() {
		return (int) nbAgents;
	}

	public void setNbAgents(double nbAgents) {
		this.nbAgents = nbAgents;
	}

	public double getNbAgentsToNextLevel() {
		return nbAgentsToNextLevel;
	}

	public Point2D.Float getPosition() {
		return position;
	}

	public Base getAssociatedBase() {
		return associatedBase;
	}

	public int getLevel() {
		return level;
	}

	public int getVision() {
		return vision;
	}
}
