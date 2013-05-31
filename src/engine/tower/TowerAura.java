package engine.tower;

import dispatcher.UnitModifier;
import engine.Unit;


public class TowerAura extends Tower {
	protected UnitModifier modifier;
	public TowerAura(int posX, int posY) {
		super(posX, posY);
	}
	
	public TowerAura(Tower other){
		super(other);
	}
	
	@Override
	public void addToUnitsInVision(Unit unitToAdd){
		if(unitToAdd.getOwner() == this.getAssociatedBase().getOwner()){
			this.unitsInVision.add(unitToAdd);
		}
	}
	
	@Override
	public void action(){		
		//apply modifier on "on range" unit
		for(Unit unit:this.unitsInVision){
			unit.addModifier(this.modifier);
		}
	}
}
