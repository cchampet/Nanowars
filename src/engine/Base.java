package engine;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector2f;

import renderer.BaseSprite;
import renderer.Sprite;

/**
 * This class represent an in-game base, physically and graphically. 
 * @author jijidici
 *
 */
public class Base{
	/**
	 * Best capacity a base can have.
	 */
	public static final int MAX_CAPACITY = 100;
	
	private int id;
	private final Vector2f position;
	private double capacity;
	private double nbAgents;
	private BaseSprite baseSprite;
	
	/**
	 * Constructor asking a 2D position and a capacity for the base.
	 * @param posX - x coordinate of base position
	 * @param posY - y coordinate of base position
	 * @param capacity - max number of agents the base can have before stopping its production
	 */
	public Base(int posX, int posY, int capacity){
		super();
	
		this.position = new Vector2f(posX, posY);
		this.nbAgents = capacity/2;
		this.capacity=capacity;
		this.baseSprite = new BaseSprite();
	}
	
	public void initSprite(BufferedImage img){
		this.baseSprite.setSize((int)this.capacity);
		this.baseSprite.setImage(img);
		this.baseSprite.setBounds((int) position.x, (int) position.y, (int)this.capacity,(int)this.capacity);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public Sprite getSprite(){
		return baseSprite;
	}
	
	public int getXCoord(){
		return (int) this.position.x;
	}
	
	public int getYCoord(){
		return (int) this.position.y;
	}
	
	public double getCapacity(){
		return this.capacity;
	}
	
	public int getNbAgents() {
		return (int) nbAgents;
	}
	
	public void prodAgents(){
		if(this.nbAgents<=this.capacity){
			this.nbAgents+=0.001*this.capacity;
		}
	}
}
