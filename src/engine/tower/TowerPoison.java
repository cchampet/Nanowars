package engine.tower;

import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import dispatcher.UnitModifier;
import engine.Projectile;
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
	
	@Override
	public void initCorrespondantProjectileSprite(Projectile projectile){
		Dispatcher.getRenderer().addProjectileSprite(projectile, TypeOfTower.POISON); //it's bad !
	}
}
