package engine;

import java.awt.geom.Point2D;

public abstract class Element {
	private int id;
	public Point2D.Float position;
	protected double nbAgents;

	public Element(int posX, int posY, double nbAgents){
		this.position = new Point2D.Float(posX, posY);
		this.nbAgents = nbAgents;
	}
	
	public double distanceToElement(Element other){
		return Math.sqrt(
						   (other.getPosition().getX()-this.getPosition().getX())
						   *(other.getPosition().getX()-this.getPosition().getX())
													+
						   (other.getPosition().getY()-this.getPosition().getY())
						   *(other.getPosition().getY()-this.getPosition().getY())
						);
	}
	
	// GETTERS & SETTERS
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Point2D.Float getPosition() {
		return position;
	}
	
	public int getNbAgents() {
		return (int) nbAgents;
	}
	
	public void setNbAgents(double nbAgents) {
		this.nbAgents = nbAgents;
	}
	
	// ABSTRACT METHODS

	public abstract Point2D.Float getCenter();
}
