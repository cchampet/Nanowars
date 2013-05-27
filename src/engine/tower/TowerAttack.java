package engine.tower;

import engine.Unit;


public class TowerAttack extends Tower {
	/**
	 * ATTACK_COUNTER_LIMIT allows to fix the attack speed of the Tower
	 */
	private static int ATTACK_COUNTER_LIMIT = 20;
	private int damage;
	private int attackCounter=0;
	//temporary flag to practise the projectile system
	private boolean hasHitTarget = false;

	public TowerAttack(int posX, int posY) {
		super(posX, posY);
		this.damage = 0;
	}
	
	public TowerAttack(Tower other){
		super(other);
		this.damage = 0;
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
	}
	
	public void action(){
		if(unitsInVision.size()>0){
			if(attackCounter>=ATTACK_COUNTER_LIMIT){
				while(this.distanceToElement(unitsInVision.getFirst())>this.vision 
						|| !unitsInVision.getFirst().isAliveFlag()
						|| unitsInVision.getFirst().getOwner().equals(this.getAssociatedBase().getOwner())){
					unitsInVision.removeFirst();
					if(unitsInVision.size()==0){
						break;
					}
				}
				if(unitsInVision.size()>0){
					// TODO: Ici creer un Projectile
					/*
					 * Un Projectile a comme attribut 
					 * - une position
					 * - une unité cible
					 * - un flag "a touché"
					 * Lui donner comme unité cible unitsInVision.getFirst()
					 * Quand le Projectile arrive sur l'unité, son flag "a touché" passe à vrai
					 */
					this.hasHitTarget = true;
					// Fin TO DO
					attackCounter=0;
				}
			}
			else{
				attackCounter++;
			}
		}
		
		//Manage Tower effect
		if(this.hasHitTarget){
			applyEffect(this.unitsInVision.getFirst());
			this.hasHitTarget = false;
			//TODO: Ici remplacer
			/*
			 * - Le if par un test sur le flag "a touché" du Projectile
			 * - Passer l'unité cible du Projectile a applyEffect
			 */
		}
	}
	
	/**
	 * Abstract function to apply an effect on the targeted unit. This function is call when a tower's projectile hit its target.
	 * The applied effect is implemented in daughter classes.
	 * @param targetedUnit Unit on which apply a special effect.
	 */
	public void applyEffect(Unit targetedUnit){}
	
	// GETTERS & SETTERS
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
