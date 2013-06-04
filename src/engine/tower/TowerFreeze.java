package engine.tower;

import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import dispatcher.UnitModifier;
import engine.Projectile;
import engine.Unit;

public class TowerFreeze extends TowerAttack {

	public TowerFreeze(int posX, int posY) {
		super(posX, posY);
		this.updateStats();
	}
	
	public TowerFreeze(Tower other){
		super(other);
		this.updateStats();
	}
	
	@Override
	public void updateStats(){
		super.updateStats();
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
	
	@Override
	public void initCorrespondantProjectileSprite(Projectile projectile){
		Dispatcher.getRenderer().addProjectileSprite(projectile, TypeOfTower.FREEZE); //it's bad !
	}
}
