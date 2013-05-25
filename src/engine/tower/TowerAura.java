package engine.tower;

import dispatcher.UnitModifier;


public class TowerAura extends Tower {
	protected UnitModifier modifier;
	public TowerAura(int posX, int posY) {
		super(posX, posY);
	}
	
	public TowerAura(Tower other){
		super(other);
	}
	
	@Override
	public void action(){
		while(!this.unitsInVision.isEmpty()){
			this.unitsInVision.getFirst().addModifier(this.modifier);
			this.unitsInVision.removeFirst();
		}
	}
}
