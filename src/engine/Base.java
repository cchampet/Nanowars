package engine;

import java.awt.geom.Point2D;

import playable.Player;
import playable.TypeOfPlayer;
import dispatcher.Dispatcher;

/**
 * This class represent an in-game base, physically.
 * @author jijidici
 *
 */
public class Base extends Element {
	/**
	 * Best capacity a base can have.
	 */
	public static final int MAX_CAPACITY = 100;
	
	private double capacity;
	private Player owner;
	/**
	 * 
	 */
	private boolean ownerChanged;
		
	/**
	 * Constructor asking a 2D position and a capacity for the base.
	 * @param posX - x coordinate of base position
	 * @param posY - y coordinate of base position
	 * @param capacity - max number of agents the base can have before stopping its production
	 * @param player - the owner of the base
	 */
	public Base(int posX, int posY, int capacity, Player player){
		super(posX, posY, capacity/2);
	
		this.capacity = capacity;
		this.owner = player;
		this.ownerChanged = false;
	}
	
	public void prodAgents(){
		if(this.isANeutralBase()) //if it's a neutral base, it doesn't product
			return;
		if(this.nbAgents<=this.capacity){
			this.nbAgents+=0.0005*this.capacity;
		}
	}

	public void reduceNbAgents(double nbAgentsOfUnitSent) {
		this.nbAgents -= nbAgentsOfUnitSent;
	}
	
	public void increaseNbAgents(double nbAgentsOfUnitSent) {
		this.nbAgents += nbAgentsOfUnitSent;
	}
	
	public Unit sendUnit(double nbAgentsOfUnitSent, Element endingElement) {
		Unit newUnit = new Unit(nbAgentsOfUnitSent, this, endingElement);
		this.reduceNbAgents(nbAgentsOfUnitSent);
		
		newUnit.setId(Dispatcher.getRenderer().addUnitSprite(newUnit));
		Dispatcher.getEngine().addUnit(newUnit);
		
		return newUnit;
	}
	
	public void makeTheChangeOfCamp() {
		if(this.nbAgents < 0)
			this.nbAgents *= -1;
	}
	
	// GETTERS & SETTERS
	
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
	
	public boolean isFull(){
		return this.nbAgents < this.capacity ? false : true;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player newOwner) {
		this.owner = newOwner;
	}
	
	public static int getMaxCapacity() {
		return MAX_CAPACITY;
	}
	
	public boolean isOwnerChanged() {
		return ownerChanged;
	}

	public void setOwnerChanged(boolean ownerChanged) {
		this.ownerChanged = ownerChanged;
	}

	public boolean isANeutralBase() {
		return owner == null ? true : false;
	}
	
	public boolean isAPlayerBase() {
		if(this.isANeutralBase())
			return false;
		return owner.getType() == TypeOfPlayer.PLAYER ? true : false;
	}
}
