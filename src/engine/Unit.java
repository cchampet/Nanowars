package engine;

import java.awt.geom.Point2D;

import playable.Player;
import playable.TypeOfPlayer;
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

	public void move(){
		this.position.setLocation(this.position.x + (this.direction.x * this.moveSpeed), 
				this.position.y + (this.direction.y * this.moveSpeed));
	}
	
	public boolean atDestination(){
		if(this.position.distance(this.goal.getCenter()) < 10) {
			//resolve the attack
			if(this.goal.getOwner() != this.owner){
				if(this.nbAgents <= this.goal.getNbAgents())
					this.goal.reduceNbAgents(this.nbAgents);
				else if(this.nbAgents == this.goal.getNbAgents()){
					this.goal.reduceNbAgents(this.nbAgents);
					this.goal.setOwner(Dispatcher.getPlayers().get("Neutral"));
					//update display
					Dispatcher.getRenderer().getSprite(this.goal.getId()).setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
					Dispatcher.getRenderer().getSprite(this.goal.getId()).repaint();
				}
				else{
					this.goal.reduceNbAgents(this.nbAgents);
					this.goal.setOwner(this.owner);
					this.goal.makeTheChangeOfCamp();
					//update display
					Dispatcher.getRenderer().getSprite(this.goal.getId()).setImage(this.goal.getOwner().getType().getImageOfBase());
					Dispatcher.getRenderer().getSprite(this.goal.getId()).repaint();
				}
			}
			else
				this.goal.increaseNbAgents(this.nbAgents);
			return true;
		}
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
