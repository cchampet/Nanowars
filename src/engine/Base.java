package engine;

import javax.vecmath.Vector2f;

/**
 * This class represent an in-game base, physically and graphically. 
 * @author jijidici
 *
 */
public class Base{
	/**
	 * Best capacity a base can have.
	 */
	public static final int MAX_CAPACITY = 100;
	
	private int id;
	private final Vector2f position;
	private double capacity;
	private double nbAgents;
		
	/**
	 * Constructor asking a 2D position and a capacity for the base.
	 * @param posX - x coordinate of base position
	 * @param posY - y coordinate of base position
	 * @param capacity - max number of agents the base can have before stopping its production
	 */
	public Base(int posX, int posY, int capacity){
		super();
	
		this.position = new Vector2f(posX, posY);
		this.nbAgents = capacity/2;
		this.capacity = capacity;
	}
	
	public void prodAgents(){
		if(this.nbAgents<=this.capacity){
			this.nbAgents+=0.001*this.capacity;
		}
	}

	public void reduceNbAgents(double nbAgentsOfUnitSent) {
		this.nbAgents -= nbAgentsOfUnitSent;
	}
	
	// GETTERS & SETTERS
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getXCoord(){
		return (int) this.position.x;
	}
	
	public int getYCoord(){
		return (int) this.position.y;
	}
	
	public Vector2f getCenter() {
		return new Vector2f((float) (this.position.x + this.capacity / 2), (float) (this.position.y + this.capacity / 2));
	}
	
	public int getCapacity(){
		return (int) this.capacity;
	}
	
	public int getNbAgents() {
		return (int) nbAgents;
	}
}
