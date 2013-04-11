package engine;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.vecmath.Vector2f;

public class Base {
	public static final int MAX_CAPACITY = 100;
	
	private final Vector2f position;
	private int capacity;
	private int nbAgents;
	private final JLabel baseSprite;
	
	public Base(int posX, int posY, int capacity){
		super();
		this.position = new Vector2f(posX, posY);
		this.capacity = capacity;
		this.nbAgents = capacity/2;
		this.baseSprite = new JLabel();
	}
	
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
