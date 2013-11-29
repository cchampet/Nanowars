package engine;

import java.awt.geom.Point2D;
import java.util.LinkedHashSet;

import playable.Player;
import dispatcher.Dispatcher;
import dispatcher.UnitModifier;
import engine.tower.Tower;

/**
 * This class represents a unit (a cluster of agents) for the engine (no display for this class).
 * @author Yuki
 *
 */
public class Unit extends Element {
	private Element goal;
	private Element start;
	private int moveSpeed = 2;
	private Player owner;
	private boolean isAliveFlag;
	private boolean isDivided;
	/**
	 * List of modifiers which affect the unit
	 */
	private final LinkedHashSet<UnitModifier> modifiers;
	
	private Point2D.Float direction;
	
	public Unit(double nbAgents, Base start, Element goal){
		super((int)start.getCenter().x, (int)start.getCenter().y, nbAgents);
		
		this.owner = start.getOwner();
		this.start = start;
		this.goal = goal;
		this.isAliveFlag = true;
		this.isDivided = false;
		this.modifiers = new LinkedHashSet<UnitModifier>();
		
		Point2D.Float startingPosition = start.getCenter();
		Point2D.Float endingPosition = goal.getCenter();
		if(this.start!=this.goal){
			this.direction = new Point2D.Float(endingPosition.x - startingPosition.x, endingPosition.y - startingPosition.y);
			float normDirection = (float) this.direction.distance(0, 0);
			this.direction.x /= normDirection;
			this.direction.y /= normDirection;
		}
		else{
			this.direction = new Point2D.Float(1,1);
		}
	}
	
	/**
	 * Move the unit along his direction vector.
	 */
	public void move(){
		//Proliferation modifier
		if(this.modifiers.contains(UnitModifier.PROLIFERATION)){
			this.nbAgents += 0.05;
		}
		
		//Poison modifier
		if(this.modifiers.contains(UnitModifier.POISONED)){
			this.nbAgents -= 0.05;
		}
		
		float coefSpeed = 1;
		//Speed modifier
		if(this.modifiers.contains(UnitModifier.SPEED)){
			coefSpeed = 2.f;
		}
		
		//Slow modifier
		if(this.modifiers.contains(UnitModifier.SLOWED)){
			coefSpeed *= 0.5f;
		}
		
		this.position.setLocation(this.position.x + (this.direction.x * this.moveSpeed * coefSpeed), 
				this.position.y + (this.direction.y * this.moveSpeed * coefSpeed));
	}
	
	/**
	 * Called when the unit is at his destination. Resolves the situation (depend on the owner of the base).
	 * @return Boolean - true if the unit have to be delete, else false
	 */
	public boolean resolveAttack(){
		//if the unit isn't destroyed
		if(this.nbAgents>0){
			//if the destination is a base
			if(this.goal.getClass() == Base.class){
				Base tmpGoal = (Base) this.goal;
				if(tmpGoal.getOwner() != this.owner){
					if(this.nbAgents <= this.goal.getNbAgents()){
						tmpGoal.reduceNbAgents(this.nbAgents);
					}
					else if(this.nbAgents == this.goal.getNbAgents()){
						tmpGoal.reduceNbAgents(this.nbAgents);
						tmpGoal.setOwner(Dispatcher.getPlayers().get("Neutral"));
						tmpGoal.setOwnerChanged(true);
					}
					else{
						tmpGoal.reduceNbAgents(this.nbAgents);
						tmpGoal.setOwner(this.owner);
						tmpGoal.setOwnerChanged(true);
						tmpGoal.makeTheChangeOfCamp();
						tmpGoal.reInitTowers();
					}
				}
				else{
					tmpGoal.increaseNbAgents(this.nbAgents);
				}
			}
			//if the destination is a tower
			else if(this.goal instanceof Tower){
				Tower tmpGoal = (Tower)this.goal;
				//Send back a unit if the target tower is full
				int sendBackAgents = tmpGoal.addNbAgents(this.nbAgents);
				if(sendBackAgents != -1){
					tmpGoal.sendUnitBackToBase(sendBackAgents, this);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return if the unit is at his destination (a base or a tower) or not.
	 */
	public boolean atDestination(){
		if(this.position.distance(this.goal.getCenter()) < 10)
			return true;
		return false;
	}
	
	/**
	 * Reduce the number of agents of a unit when attacked by a TowerAttack
	 * @param damage
	 */
	public void reduceNbAgents(double damage){		
		if(this.modifiers.contains(UnitModifier.RESISTANT)){
			damage -= 2;
			if(damage < 0) damage = 0;
		}
		
		this.nbAgents-=damage;
		//minimum 0
		if(this.nbAgents < 0){
			this.nbAgents = 0;
		}else{
			if(this.nbAgents > 1 && this.modifiers.contains(UnitModifier.DIVISION)){
				this.isDivided = true;
			}
		}
	}
	
	/**
	 * Apply a modifier to the unit
	 * @param newModifier the modifier to apply
	 */
	public void addModifier(UnitModifier newModifier){
		this.modifiers.add(newModifier);
	}
	// GETTERS & SETTERS
	
	public Element getGoal() {
		return goal;
	}
	
	public void setGoal(Element goal) {
		this.goal = goal;
	}
	
	public Player getOwner() {
		return owner;
	}

	public Point2D.Float getDirection() {
		return direction;
	}
	
	public Element getStart() {
		return start;
	}
	
	/**
	 * Change the position of the unit. Re-compute the right direction
	 * @param newPosition the new position of the unit
	 */
	public void setPosition(Point2D.Float newPosition){
		this.position = newPosition;
		this.direction.x = this.goal.getCenter().x - newPosition.x;
		this.direction.y = this.goal.getCenter().y - newPosition.y;
		double normDirection = this.direction.distance(0, 0);
		this.direction.x /= normDirection;
		this.direction.y /= normDirection;
	}
	
	public LinkedHashSet<UnitModifier> getModifiers(){
		return this.modifiers;
	}
	
	public void setStart(Element start) {
		this.start = start;
	}
	
	public void setDirectionToOpposite() {
		this.direction.x *= -1;
		this.direction.y *= -1;
	}

	@Override
	public Point2D.Float getCenter() {
		return null;
	}

	public boolean isAliveFlag() {
		return isAliveFlag;
	}
	
	public void setAliveFlag(boolean lifeFlag) {
		this.isAliveFlag = lifeFlag;
	}
	
	public boolean isDivided(){
		return this.isDivided;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}
}
