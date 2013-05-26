package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JTextField;

import dispatcher.UnitModifier;

import engine.Unit;

/**
 * This class display a unit.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class UnitSprite extends Sprite{
	
	static final private int MIN_SIZE = 20;
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding base.
	 */
	private JTextField nbAgents;
	/**
	 * engineUnit is a reference to the corresponding unit of this sprite.
	 */
	private Unit engineUnit;
	
	public UnitSprite(Unit correspondingEngineUnit) {
		super();
		this.setLayout(new BorderLayout());
		
		this.engineUnit = correspondingEngineUnit;
				
		this.nbAgents = new JTextField(String.valueOf((int)correspondingEngineUnit.getNbAgents()));
		this.nbAgents.setDisabledTextColor(new Color(255, 255, 255));
		this.nbAgents.setEnabled(false);
		this.nbAgents.setHorizontalAlignment(JTextField.CENTER);
		this.nbAgents.setBorder(null);
		this.nbAgents.setOpaque(false);
		this.nbAgents.setIgnoreRepaint(false); // for better performence
		this.add(this.nbAgents, BorderLayout.CENTER);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//update the JTextField which represents the nbAgents
		this.nbAgents.setText(String.valueOf(this.engineUnit.getNbAgents()));
		
		//update the position of the unit
		this.size = UnitSprite.MIN_SIZE + this.engineUnit.getNbAgents()/2;
		this.setLocation(new Point((int)(this.engineUnit.getPosition().x - this.size/2), (int)(this.engineUnit.getPosition().y - this.size/2)));
		this.setSize(this.size, this.size);
		
		//display the modifier icon
		int cptLayout = 0;
		for(UnitModifier mod:this.engineUnit.getModifiers()){
			if(cptLayout<2){
				g.drawImage(mod.getIcon(), this.size-10*(cptLayout+1), this.size-10, 10, 10, this);
			}else{
				g.drawImage(mod.getIcon(), this.size-10*(cptLayout/2+1), this.size-20, 10, 10, this);
			}
			cptLayout++;
		}
	}
	
	/**
	 * Change the number of agents to display
	 * @param nbAgents
	 */
	//public void updateNbAgents(int nbAgents){
		
	//}
	
	// GETTERS & SETTERS

	public Unit getEngineUnit() {
		return this.engineUnit;
	}
	
	@Override
	public void setSize(int size){
		this.size = UnitSprite.MIN_SIZE + size/2;
	}
}
