package engine;

public class TowerAttack extends Tower {
	private int DPS;
	private Unit target;

	public TowerAttack(int posX, int posY) {
		super(posX, posY);
		
		this.DPS = 5 * this.level;
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		
		this.DPS = 5 * this.level;
	}
	
	public void action(){
		for(Unit unit:Unit.getUnits()){
			if(unit.getOwner().isIA() && unit.distanceToElement(this)<this.vision){
				this.sendUnit(10, unit);
			}
		}
	}
	
	// GETTERS & SETTERS
	
	public int getDPS() {
		return DPS;
	}
	
	public void setDPS(int dPS) {
		DPS = dPS;
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
	}
}
