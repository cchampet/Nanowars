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
public class TowerSprite extends SelectedSprite implements MouseListener, ActionListener{
	/**
	 * static variable which contains a tower to build (player click on it)
	 */
	private static TowerSprite towerToBuild = null;
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
		
		//update the sprite of the base if it's necessary
		if(this.engineTower.getLevel() == 0)
			this.setImage(this.engineTower.getAssociatedBase().getOwner().getType().getImageOfTowerLvl0());
		else
			this.setImage(this.engineTower.getAssociatedBase().getOwner().getType().getImageOfTowerLvlup());
		
		//update the display of nbAgents
		if(this.engineTower.isLevelMax())
			this.nbAgents.setText("Max");
		else
			this.nbAgents.setText(String.valueOf(this.engineTower.getNbAgents()));
		
		//update the display of the level
		this.level.setText("lvl "+String.valueOf(this.engineTower.getLevel()));
		
		//set the border in yellow color if the tower is level max
		if(this.engineTower.isLevelMax())
			this.setBorder(BorderFactory.createLineBorder(Color.yellow));
		else
			this.setBorder(null);
		
		//start blinking when the tower is waiting for building (and stop when it's not)
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
		if(SelectedSprite.isThereAnEndingElement()){
			Dispatcher.getRenderer().hideRadialMenus();
		}
		
		//set ending element
		if(SelectedSprite.isThereAtLeastOneStartingElement()) {
			if(this.engineTower.getAssociatedBase().isAPlayerBase())
				SelectedSprite.endingElement = this.engineTower;
		}
		
		//Build tower
		if(!SelectedSprite.isThereAtLeastOneStartingElement() && this.engineTower.isNotBuiltYet() 
															  && this.engineTower.isWaitingForBuilding()){
			TowerSprite.towerToBuild = this;
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
	
	//About tower to build
	static public boolean isThereOneTowerToBuild(){
		if(TowerSprite.towerToBuild == null){
			return false;
		}else{
			return true;
		}
	}
	
	static public void resetTowerToBuild(){
		TowerSprite.towerToBuild = null;
	}
	
	static public TowerSprite getTowerToBuild(){
		return TowerSprite.towerToBuild;
	}
}
