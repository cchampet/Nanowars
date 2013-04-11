package engine;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.vecmath.Vector2f;

/**
 * This class represent an in-game base, physically and graphically. 
 * @author jijidici
 *
 */
public class Base {
	/**
	 * Best capacity a base can have.
	 */
	public static final int MAX_CAPACITY = 100;
	
	private final Vector2f position;
	private int capacity;
	private int nbAgents;
	private final JLabel baseSprite;
	
	/**
	 * Constructor asking a 2D position and a capacity for the base.
	 * @param posX - x coordinate of base position
	 * @param posY - y coordinate of base position
	 * @param capacity - max number of agents the base can have before stopping its production
	 */
	public Base(int posX, int posY, int capacity){
		super();
		this.position = new Vector2f(posX, posY);
		this.capacity = capacity;
		this.nbAgents = capacity/2;
		this.baseSprite = new JLabel();
	}
	
	/**
	 * Allow to initialize or modify the base appearance.
	 * @param img new sprite of the base in a <code>ImageIcon</code> variable
	 */
	public void setSprite(ImageIcon img){
		//Resize the base sprite
		int baseSize = capacity;
		Image originalImage = img.getImage();
		Image resizedImage = originalImage.getScaledInstance(baseSize, baseSize, Image.SCALE_FAST);
		ImageIcon newIcon = new ImageIcon(resizedImage);
		
		//Attach the base sprite
		this.baseSprite.setIcon(newIcon);
		this.baseSprite.setBounds((int)this.position.x - baseSize/2, (int)this.position.y - baseSize/2, baseSize, baseSize);
	}
	
	/**
	 * Get the base sprite
	 * @return a <code>ImageIcon</code> which represents the Base sprite
	 */
	public JLabel getSprite(){
		return this.baseSprite;
	}
	
	public int getXCoord(){
		return (int) this.position.x;
	}
	
	public int getYCoord(){
		return (int) this.position.y;
	}
	
	public int getCapacity(){
		return this.capacity;
	}
}
