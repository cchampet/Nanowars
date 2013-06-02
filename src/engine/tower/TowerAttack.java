package engine.tower;

import java.util.LinkedList;

import dispatcher.Dispatcher;
import engine.Projectile;
import engine.Unit;


public abstract class TowerAttack extends Tower {
	/**
	 * ATTACK_COUNTER_LIMIT allows to fix the attack speed of the Tower
	 */
	private static int ATTACK_COUNTER_LIMIT = 20;
	private int damage;
	private int attackCounter=0;
	private LinkedList<Projectile> projectiles;

	public TowerAttack(int posX, int posY) {
		super(posX, posY);
		this.damage = 0;
		projectiles = new LinkedList<Projectile>();
	}
	
	public TowerAttack(Tower other){
		super(other);
		this.damage = 0;
		projectiles = new LinkedList<Projectile>(); // To be verified
	}
	
	@Override
	public void addToUnitsInVision(Unit unitToAdd){
		if(unitToAdd.getOwner() != this.getAssociatedBase().getOwner()){
			this.unitsInVision.add(unitToAdd);
		}
	}
	
	@Override
	public void action(){
		//remove far enough units
		boolean cleanUnitList = true;
		while(cleanUnitList){ // there was while(cleanList) but cleanList is defined to false right before!
			cleanUnitList = false;
			for(Unit unit:this.unitsInVision){
				if(unit.distanceToElement(this) > this.vision){
					this.unitsInVision.remove(unit);
					cleanUnitList = true;
					break;
				}	
			}
		}
		
		//Attack if there is target
		if(attackCounter>=ATTACK_COUNTER_LIMIT){
			if(this.unitsInVision.size() > 0){
				Projectile projectile = new Projectile(this.getCenter());
				projectiles.add(projectile);
				Dispatcher.getRenderer().addProjectileSprite(projectile); //it's bad !
				for(Unit unit:unitsInVision){
					projectile.setAimedUnit(unit);
					break;
				}
				attackCounter=0;
			}
		}
		else{
			attackCounter++;
		}
		
		//Manage Tower effect
		boolean cleanProjectileList = true;
		while(cleanProjectileList){
			cleanProjectileList=false;
			if(projectiles.size()>0){	
				for(Projectile projectile:projectiles){
					if(projectile.hasTouchedFlag()){	
						applyEffect(projectile.getAimedUnit());
						projectiles.remove(projectile);
						cleanProjectileList=true;
						break;
					}
					else{
						projectile.move();
					}
				}	
			}
		}
	}
	
	/**
	 * Abstract function to apply an effect on the targeted unit. This function is call when a tower's projectile hit its target.
	 * The applied effect is implemented in daughter classes.
	 * @param targetedUnit Unit on which apply a special effect.
	 */
	public void applyEffect(Unit aimedUnit){
	}
	
	// GETTERS & SETTERS
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
