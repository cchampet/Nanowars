package engine.tower;

import dispatcher.UnitModifier;
import engine.Unit;

public class TowerFreeze extends TowerAttack {

	public TowerFreeze(int posX, int posY) {
		super(posX, posY);
		this.setDamage(this.level);
	}
	
	public TowerFreeze(Tower other){
		super(other);
		this.setDamage(this.level);
	}
	
	@Override
	public void levelUp(){
		super.levelUp();
		this.setDamage(this.level);
	}
	
	/**
	 * Apply slow modifier
	 */
	@Override
	public void applyEffect(Unit targetedUnit){
		targetedUnit.addModifier(UnitModifier.SLOWED);
		targetedUnit.reduceNbAgents(this.getDamage());
	}
}
