package engine.tower;

import dispatcher.UnitModifier;

public class TowerDivision extends TowerAura {

	public TowerDivision(int posX, int posY) {
		super(posX, posY);
		this.modifier = UnitModifier.DIVISION;
	}
	
	public TowerDivision(Tower other){
		super(other);
		this.modifier = UnitModifier.DIVISION;
	}

}
