package engine.tower;

import dispatcher.UnitModifier;

public class TowerSpeed extends TowerAura {

	public TowerSpeed(int posX, int posY) {
		super(posX, posY);
		this.modifier = UnitModifier.SPEED;
	}
	
	public TowerSpeed(Tower other){
		super(other);
		this.modifier = UnitModifier.SPEED;
	}
}
