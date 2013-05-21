package engine;

public class TowerAttack extends Tower {
	private int attackSpeed=50;
	private int damage;
	private Unit target;
	private int attackCounter=0;

	public TowerAttack(int posX, int posY) {
		super(posX, posY);
		
		this.damage = 5 * this.level;
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		
		this.damage = 5 * this.level;
	}
	
	public void action(Unit unit){
		if(attackCounter==attackSpeed){
			unit.reduceNbAgents(damage);	
			attackCounter=0;
		}
		else{
			attackCounter++;
		}
	}
	
	// GETTERS & SETTERS
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
	}
}
