package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import engine.Tower;

@SuppressWarnings("serial")
public class TowerSprite extends Sprite implements MouseListener{
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding base.
	 */
	private JTextField nbAgents;
	/**
	 * engineBase is a reference to the corresponding base of this sprite.
	 */
	private Tower engineTower;

	public TowerSprite(Tower newTower) {
		super();
		
		this.engineTower = newTower;
		
		this.nbAgents = new JTextField(String.valueOf(newTower.getNbAgents()));
		this.nbAgents.setPreferredSize(new Dimension(23, 20));
		this.nbAgents.setDisabledTextColor(new Color(255, 255, 255));
		this.nbAgents.setEnabled(false);
		this.nbAgents.setBorder(null);
		this.nbAgents.setOpaque(false);
		this.nbAgents.setIgnoreRepaint(false); // for better performence
		this.add(this.nbAgents, BorderLayout.CENTER);
		
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	// GETTERS & SETTERS

	/**
	 * @return the nbAgents
	 */
	public JTextField getNbAgents() {
		return nbAgents;
	}

	/**
	 * @param nbAgents the nbAgents to set
	 */
	public void setNbAgents(JTextField nbAgents) {
		this.nbAgents = nbAgents;
	}

	/**
	 * @return the engineTower
	 */
	public Tower getEngineTower() {
		return engineTower;
	}
}
