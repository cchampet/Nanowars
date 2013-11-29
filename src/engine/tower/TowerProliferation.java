package engine.tower;

import dispatcher.UnitModifier;

public class TowerProliferation extends TowerAura {

	public TowerProliferation(int posX, int posY) {
		super(posX, posY);
		this.modifier = UnitModifier.PROLIFERATION;
	}
	
	public TowerProliferation(Tower other){
		super(other);
		this.modifier = UnitModifier.PROLIFERATION;
	}
}
