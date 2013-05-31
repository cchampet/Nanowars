package engine.tower;

import dispatcher.UnitModifier;
import engine.Unit;

public class TowerPoison extends TowerAttack {

	public TowerPoison(int posX, int posY) {
		super(posX, posY);
	}
	
	public TowerPoison(Tower other){
		super(other);
	}
	
	/**
	 * Apply poison on target unit
	 */
	@Override
	public void applyEffect(Unit targetedUnit){
		targetedUnit.addModifier(UnitModifier.POISONED);
	}
}
