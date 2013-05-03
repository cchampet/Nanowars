package engine;

import java.awt.geom.Point2D;

import javax.vecmath.Vector2f;

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
	
	public Point2D.Float position;
	private Point2D.Float start;
	private Point2D.Float end;
	private Vector2f direction;
	
	public Unit(double nbAgents, Point2D.Float firstPosition, Point2D.Float destination, Base goal){
		this.nbAgents = nbAgents;
		this.goal = goal;
		
		this.start = new Point2D.Float(firstPosition.x, firstPosition.y);
		this.position = new Point2D.Float(firstPosition.x, firstPosition.y);
		this.end = new Point2D.Float(destination.x, destination.y);
		
		this.direction = new Vector2f(destination.x - firstPosition.x, destination.y - firstPosition.y);
		this.direction.normalize();
	}
	
	public void move(){
		this.position.setLocation(this.position.x + (this.direction.x * this.moveSpeed), 
				this.position.y + (this.direction.y * this.moveSpeed));
	}
	
	public boolean atDestination(){
		if(this.position.distance(this.end) < 10) {
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

	public Point2D.Float getPosition() {
		return position;
	}

	public Point2D.Float getStart() {
		return start;
	}

	public Point2D.Float getEnd() {
		return end;
	}

	public Vector2f getDirection() {
		return direction;
	}
}
