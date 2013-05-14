package engine;

import java.awt.geom.Point2D;

import playable.Player;
import dispatcher.Dispatcher;

/**
 * This class represents a unit (a cluster of agents) for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Unit{
	private int id;
	private double nbAgents;
	private Base goal;
	private int moveSpeed = 2;
	private Player owner;
	
	public Point2D.Float position;
	private Point2D.Float direction;
	
	public Unit(double nbAgents, Base start, Base goal){
		this.nbAgents = nbAgents;
		this.goal = goal;
		this.owner = start.getOwner();

		Point2D.Float startingPosition = start.getCenter();
		Point2D.Float endingPosition = goal.getCenter();
		
		this.position = new Point2D.Float(startingPosition.x, startingPosition.y);
		
		this.direction = new Point2D.Float(endingPosition.x - startingPosition.x, endingPosition.y - startingPosition.y);
		float normDirection = (float) this.direction.distance(0, 0);
		this.direction.x /= normDirection;
		this.direction.y /= normDirection;
	}

	/**
	 * Move the unit along his direction vector.
	 */
	public void move(){
		this.position.setLocation(this.position.x + (this.direction.x * this.moveSpeed), 
				this.position.y + (this.direction.y * this.moveSpeed));
	}
	
	/**
	 * Called when the unit is at his destination. Resolves the situation (depend on the owner of the base).
	 */
	public void resolveAttack(){
		//resolve the attack 
		if(this.goal.getOwner() != this.owner){
			if(this.nbAgents <= this.goal.getNbAgents()){
				this.goal.reduceNbAgents(this.nbAgents);
			}
			else if(this.nbAgents == this.goal.getNbAgents()){
				this.goal.reduceNbAgents(this.nbAgents);
				this.goal.setOwner(Dispatcher.getPlayers().get("Neutral"));
			}
			else{
				this.goal.reduceNbAgents(this.nbAgents);
				this.goal.setOwner(this.owner);
				this.goal.makeTheChangeOfCamp();
			}
		}
		else{
			this.goal.increaseNbAgents(this.nbAgents);
		}
	}
	
	/**
	 * Return if the unit is at his destination (a base) or not.
	 */
	public boolean atDestination(){
		if(this.position.distance(this.goal.getCenter()) < 10)
			return true;
		return false;
	}

	// GETTERS & SETTERS
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public double getNbAgents() {
		return nbAgents;
	}
	
	public Base getGoal() {
		return goal;
	}
	
	public Player getOwner() {
		return owner;
	}

	public Point2D.Float getPosition() {
		return position;
	}

	public Point2D.Float getDirection() {
		return direction;
	}
}
