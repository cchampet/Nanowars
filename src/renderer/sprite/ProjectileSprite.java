package renderer.sprite;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;

import engine.Projectile;


@SuppressWarnings("serial")
public class ProjectileSprite extends Sprite {	
	/**
	 * engineProjectile is a reference to the corresponding projectile of this sprite.
	 */
	private Projectile engineProjectile;
	
	public ProjectileSprite(Projectile newProjectile) {
		super();
		this.setLayout(new BorderLayout());
		
		this.size = 16;
		
		this.engineProjectile = newProjectile;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		//update the position of the projectile
		this.setLocation(new Point((int)(this.engineProjectile.getPosition().x - this.size/2), (int)(this.engineProjectile.getPosition().y - this.size/2)));
	}
	
	// GETTERS
	
	public Projectile getEngineProjectile(){
		return engineProjectile;
	}
}
