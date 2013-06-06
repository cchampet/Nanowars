package engine;

import java.awt.geom.Point2D;

import renderer.sprite.UnitSprite;

public class Projectile {

	private Point2D.Float position;
	private Unit aimedUnit;
	private boolean hasTouchedFlag=false;
	private Point2D.Float direction;
	private int moveSpeed = 5;
	
	public Projectile(Point2D.Float position){
		this.position = position;
	}
	
	public void move(){
		if(this.position.distance(aimedUnit.getPosition())<=(UnitSprite.getMinSize()+aimedUnit.getNbAgents()/2)){
			this.hasTouchedFlag=true;
		}
		else{
			Point2D.Float startingPosition = this.position;
			Point2D.Float endingPosition = aimedUnit.getPosition();
			this.direction = new Point2D.Float(endingPosition.x - startingPosition.x, endingPosition.y - startingPosition.y);
			float normDirection = (float) this.direction.distance(0, 0);
			this.direction.x /= normDirection;
			this.direction.y /= normDirection;
			this.position.setLocation(this.position.x + (this.direction.x * this.moveSpeed), 
					this.position.y + (this.direction.y * this.moveSpeed));
		}
	}
	
	// GETTERS & SETTERS
	
	public Point2D.Float getPosition() {
		return position;
	}
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}
	public Unit getAimedUnit() {
		return aimedUnit;
	}
	public void setAimedUnit(Unit aimedUnit) {
		this.aimedUnit = aimedUnit;
		Point2D.Float startingPosition = this.position;
		Point2D.Float endingPosition = aimedUnit.getPosition();
		this.direction = new Point2D.Float(endingPosition.x - startingPosition.x, endingPosition.y - startingPosition.y);
		float normDirection = (float) this.direction.distance(0, 0);
		this.direction.x /= normDirection;
		this.direction.y /= normDirection;
	}
	
	public boolean hasTouchedFlag() {
		return hasTouchedFlag;
	}
	public void setHasTouchedFlag(boolean hasTouchedFlag) {
		this.hasTouchedFlag = hasTouchedFlag;
	}	
}




