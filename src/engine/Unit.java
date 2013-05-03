package engine;

import javax.vecmath.Vector2f;

/**
 * This class represents a unit (a cluster of agents) for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Unit{
	private int id;
	private int nbAgents;
	public Vector2f position;
	private Vector2f start;
	private Vector2f end;
	private Vector2f direction;
	public int moveSpeed = 2;
	
	public Unit(int nbAgents, Vector2f firstPosition, Vector2f destination){
		this.nbAgents = nbAgents;
		this.start = new Vector2f(firstPosition);
		this.position = new Vector2f(firstPosition);
		this.end = new Vector2f(destination);
		
		this.direction = new Vector2f(destination);
		this.direction.add(new Vector2f(-firstPosition.x, -firstPosition.y));
		this.direction.normalize();
	}
	
	public void move(){
		this.position.add(this.direction);
		//System.out.println("New Position of the Unit : "+this.position.x+", "+this.position.y);
	}
	
	public boolean atDestination(){
		if(this.position.epsilonEquals(this.end, 0.5f))
			return true;
		else
			return false;
	}

	// GETTERS & SETTERS
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getNbAgents() {
		return nbAgents;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getStart() {
		return start;
	}

	public Vector2f getEnd() {
		return end;
	}

	public Vector2f getDirection() {
		return direction;
	}
}
