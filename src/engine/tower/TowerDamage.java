package engine.tower;

import engine.Unit;

public class TowerDamage extends TowerAttack {

	public TowerDamage(int posX, int posY) {
		super(posX, posY);
		this.updateStats();
	}
	
	public TowerDamage(Tower other){
		super(other);
		this.updateStats();
	}
	
	@Override
	public void updateStats(){
		super.updateStats();
		this.setDamage(2+2*this.level);
	}
	
	@Override
	public void applyEffect(Unit targetedUnit){
		targetedUnit.reduceNbAgents(this.getDamage());
	}
}
