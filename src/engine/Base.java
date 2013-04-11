package engine;

import javax.vecmath.Vector2f;

public class Base {
	private final Vector2f position;
	private int capacity;
	
	public Base(int posX, int posY, int capacity){
		this.position = new Vector2f(posX, posY);
		this.capacity = capacity;
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
