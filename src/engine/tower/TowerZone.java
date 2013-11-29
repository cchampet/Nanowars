package engine.tower;

import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import engine.Projectile;
import engine.Unit;

public class TowerZone extends TowerAttack {

	public TowerZone(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
	}
	
	public TowerZone(Tower other){
		super(other);
	}
	
	@Override
	public void createProjectile(){
		for(Unit unit:unitsInVision){
			Projectile projectile = new Projectile(this.getCenter());
			projectiles.add(projectile);
			initCorrespondantProjectileSprite(projectile);
			projectile.setAimedUnit(unit);
		}
	}
	
	@Override
	public void initCorrespondantProjectileSprite(Projectile projectile){
		Dispatcher.getRenderer().addProjectileSprite(projectile, TypeOfTower.ZONE); //it's bad !
	}
	
	@Override
	public void updateStats(){
		super.updateStats();
		this.setDamage(this.level);
	}
	
	@Override
	public void applyEffect(Unit targetedUnit){
		targetedUnit.reduceNbAgents(this.getDamage());
	}
}
