package engine.tower;

import dispatcher.UnitModifier;

public class TowerResistant extends TowerAura {

	public TowerResistant(int posX, int posY) {
		super(posX, posY);
		this.modifier = UnitModifier.RESISTANT;
	}
	
	public TowerResistant(Tower other){
		super(other);
		this.modifier = UnitModifier.RESISTANT;
	}
}
