package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Timer;

import javax.swing.JTextField;

import engine.Unit;

@SuppressWarnings("serial")
public class UnitSprite extends Sprite{
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
		
		this.engineUnit = correspondingEngineUnit;
				
		this.nbAgents = new JTextField(String.valueOf(correspondingEngineUnit.getNbAgents()));
		this.nbAgents.setDisabledTextColor(new Color(255, 255, 255));
		this.nbAgents.setEnabled(false);
		this.nbAgents.setBorder(null);
		this.nbAgents.setOpaque(false);
		this.nbAgents.setIgnoreRepaint(false); // for better performence
		this.add(this.nbAgents, BorderLayout.CENTER);
	}
}
