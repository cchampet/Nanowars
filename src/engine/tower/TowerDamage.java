package engine.tower;

import engine.Unit;

public class TowerDamage extends TowerAttack {

	public TowerDamage(int posX, int posY) {
		super(posX, posY);
		this.setDamage(2+2*this.level);
	}
	
	public TowerDamage(Tower other){
		super(other);
		this.setDamage(2+2*this.level);
	}
	
	@Override
	public void levelUp(){
		super.levelUp();
		this.setDamage(2+2*this.level);
	}
	
	@Override
	public void applyEffect(Unit targetedUnit){
		targetedUnit.reduceNbAgents(this.getDamage());
	}
}
