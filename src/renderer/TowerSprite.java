package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

import dispatcher.Dispatcher;

import engine.Tower;

/**
 * This class display a Tower.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class TowerSprite extends ElementSprite implements MouseListener, ActionListener{
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding tower.
	 */
	private JTextField nbAgents;
	/**
	 * level is the JTextField which is used to display the level of the correpsonding tower.
	 */
	private JTextField level;
	/**
	 * timer and blink are useful to create the blink when the tower is waiting for building.
	 */
	private Timer timer; 
	private boolean blink;
	/**
	 * engineBase is a reference to the corresponding base of this sprite.
	 */
	private Tower engineTower;

	public TowerSprite(Tower newTower) {
		super();
		this.setLayout(new BorderLayout());
		
		this.engineTower = newTower;
		
		this.timer = new Timer (500, this);
	    this.blink = false;
		
		this.size = 24;
		
		this.nbAgents = new JTextField(String.valueOf(newTower.getNbAgents()));
		this.nbAgents.setPreferredSize(new Dimension(20, 20));
		this.nbAgents.setDisabledTextColor(new Color(255, 255, 255));
		this.nbAgents.setEnabled(false);
		this.nbAgents.setBorder(null);
		this.nbAgents.setHorizontalAlignment(JTextField.CENTER);
		this.nbAgents.setOpaque(false);
		this.nbAgents.setIgnoreRepaint(false); // for better performence
		this.nbAgents.addMouseListener(this);
		this.add(this.nbAgents, BorderLayout.CENTER);
		
		this.level = new JTextField("lvl "+String.valueOf(newTower.getLevel()));
		this.level.setFont(new Font(this.level.getFont().getName(), this.level.getFont().getStyle(), 8));
		this.level.setPreferredSize(new Dimension(10, 10));
		this.level.setDisabledTextColor(new Color(255, 255, 255));
		this.level.setEnabled(false);
		this.level.setBorder(null);
		this.level.setHorizontalAlignment(JTextField.CENTER);
		this.level.setOpaque(true);
		this.level.setBackground(new Color(0, 0, 0));
		this.level.setIgnoreRepaint(false); // for better performence
		this.level.addMouseListener(this);
		this.add(this.level, BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(this.engineTower.isLevelMax())
			this.setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		if(this.engineTower.isWaitingForBuilding())
			this.timer.start();
		else
			this.timer.stop();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//check if the player isn't choosing unit on another base
		if(TowerSprite.isThereAnEndingElement()){
			Dispatcher.getRenderer().hideRadialMenuMovment();
		}
		
		//set ending element
		if (BaseSprite.isThereAtLeastOneStartingElement()) {
			if(this.engineTower.getAssociatedBase().isAPlayerBase())
				TowerSprite.endingElement = this.engineTower;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	/**
	 * method called each blinks (when the tower is waiting for building).
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (blink){
      	  this.setBorder(BorderFactory.createLineBorder(Color.gray));
      	  this.blink = false;
		}
        else{
      	  this.setBorder(BorderFactory.createLineBorder(Color.black));
      	  this.blink = true;
        }
	}
	
	// GETTERS & SETTERS

	public JTextField getNbAgents() {
		return nbAgents;
	}

	public void setNbAgents(JTextField nbAgents) {
		this.nbAgents = nbAgents;
	}
	
	public JTextComponent getLevel() {
		return this.level;
	}
	
	public Tower getEngineTower() {
		return engineTower;
	}
}
