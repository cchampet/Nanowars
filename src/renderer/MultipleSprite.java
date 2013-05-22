package renderer;

import java.awt.Graphics;

/**
 * Class use to represent sprite composed with multiple image. This sprite acts like a mask on its background image (similar process as in CSS)
 * @author Jijidici
 *
 */
@SuppressWarnings("serial")
public class MultipleSprite extends Sprite {
	/**
	 * Number of the displayed sprite
	 */
	private int currentSprite;
	/**
	 * Total number of different figure inside the image
	 */
	private int nbSprites;
	
	public MultipleSprite(int nbSprites){
		super();
		this.currentSprite = 0;
		this.nbSprites = nbSprites;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if(image != null){
			g.drawImage(image, 0, -this.currentSprite*size, size, nbSprites*size, this);
		}
	}
	
	/**
	 * Change multiple sprite state to display the chosen sprite
	 * @param id number of the sprite to display 
	 */
	public void goToSprite(int id){
		this.currentSprite = id;
	}
}
