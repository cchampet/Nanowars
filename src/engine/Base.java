package engine;

import java.awt.geom.Point2D;

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
	private final Point2D.Float position;
	private double capacity;
	private double nbAgents;
	private int owner;
		
	/**
	 * Constructor asking a 2D position and a capacity for the base.
	 * @param posX - x coordinate of base position
	 * @param posY - y coordinate of base position
	 * @param capacity - max number of agents the base can have before stopping its production
	 * @param owner - the owner of the base
	 */
	public Base(int posX, int posY, int capacity, int owner){
		super();
	
		this.position = new Point2D.Float(posX, posY);
		this.nbAgents = capacity/2;
		this.capacity = capacity;
		this.owner = owner;
	}
	
	public void prodAgents(){
		if(this.isNeutral()) //if it's a neutral base, it doesn't product
			return;
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
	
	public Point2D.Float getCenter() {
		return new Point2D.Float(this.position.x + (int)this.capacity / 2, this.position.y + (int)this.capacity / 2);
	}
	
	public int getCapacity(){
		return (int) this.capacity;
	}
	
	public int getNbAgents() {
		return (int) nbAgents;
	}

	public int getOwner() {
		return owner;
	}

	public boolean isNeutral() {
		return owner == 0 ? true : false;
	}
	
	public boolean isAPlayersBase() {
		return owner == 3 ? true : false;
	}
}
