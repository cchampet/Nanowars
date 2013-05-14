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
