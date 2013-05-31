package engine.tower;

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
	private Projectile projectile;

	public TowerAttack(int posX, int posY) {
		super(posX, posY);
		this.damage = 0;
	}
	
	public TowerAttack(Tower other){
		super(other);
		this.damage = 0;
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
		boolean cleanList = false;
		while(cleanList){
			cleanList = true;
			for(Unit unit:this.unitsInVision){
				if(unit.distanceToElement(this) > this.vision){
					this.unitsInVision.remove(unit);
					cleanList = false;
					break;
				}	
			}
		}
		
		//Attack if there is target
		if(attackCounter>=ATTACK_COUNTER_LIMIT){
			if(this.unitsInVision.size() > 0){
				projectile = new Projectile(this.getCenter());
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
		if(projectile!=null){	
			if(projectile.hasTouchedFlag()){	
				applyEffect(projectile.getAimedUnit());
				projectile=null;
			}
			else{
				projectile.move();
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
