package engine;

import javax.vecmath.Vector2f;

/**
 * This class represents a unit (a cluster of agents) for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Unit {
	private int nbAgents;
	public Vector2f position;
	private Vector2f start;
	private Vector2f end;
	private Vector2f direction;
	
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
	}
	
	public boolean atDestination(){
		if(this.position.epsilonEquals(this.end, 0.5f))
			return true;
		else
			return false;
	}
}
