package engine;

public class TowerAttack extends Tower {
	/**
	 * attackCounterLimit allows to fix the speed of the Tower attacks
	 */
	private int attackCounterLimit=40;
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
		
		this.damage = 2 * this.level;
	}
	
	public void action(){
		if(unitsInVision.size()>0){
			if(attackCounter==attackCounterLimit){
				while((this.distanceToElement(unitsInVision.getFirst())>this.vision 
						|| !unitsInVision.getFirst().isAliveFlag())){
					unitsInVision.removeFirst();
					if(unitsInVision.size()==0){
						break;
					}
				}
				if(unitsInVision.size()>0){
					unitsInVision.getFirst().reduceNbAgents(damage);
					attackCounter=0;
				}
			}
			else{
				attackCounter++;
			}
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
