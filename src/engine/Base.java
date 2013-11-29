package engine;

import java.awt.geom.Point2D;

import playable.Player;
import playable.TypeOfPlayer;
import renderer.TowerSprite;
import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import engine.tower.Tower;

/**
 * This class represent an in-game base, physically.
 * @author jijidici
 *
 */
public class Base extends Element {
	/**
	 * Best capacity a base can have.
	 */
	public static final int MAX_CAPACITY = 200;
	
	private double capacity;
	private Player owner;
	/**
	 * ownerChanged is a flag, up when the owner of the base has just changed.
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
	
	/**
	 * Reset its towers to basic level 0 tower
	 */
	public void reInitTowers(){
		for(Tower t:Dispatcher.getEngine().getTowerAround(this)){
			//if the tower is not  built
			if(t.getLevel() == 0){
				t.destroyTower();
			}
			else{
				//forbidden tower construction
				if(TowerSprite.getTowerToBuild() != null){
					if(t.equals(TowerSprite.getTowerToBuild().getEngineTower())){
						TowerSprite.resetTowerToBuild();
						Dispatcher.getRenderer().hideRadialMenus();
					}
				}
				//build a new basic tower instead
				Tower basicTower = Dispatcher.getEngine().unspecializeTower(t);
				Dispatcher.getRenderer().updateTowerSprite(basicTower, t.getId(), TypeOfTower.NONE);
			}
		}
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
