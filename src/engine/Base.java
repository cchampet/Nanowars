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
