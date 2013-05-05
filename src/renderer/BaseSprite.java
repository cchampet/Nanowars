package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import engine.Base;

/**
 * This class display a base.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class BaseSprite extends Sprite implements MouseListener{
	/**
	 * startingPoint and endingPoint are static variable, useful to decide in which direction the player sends units.
	 */
	private static Base startingPoint;
	private static Base endingPoint;
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding base.
	 */
	private JTextField nbAgents;
	/**
	 * engineBase is a reference to the corresponding base of this sprite.
	 */
	private Base engineBase;

	public BaseSprite(Base newBase) {
		super();
		
		this.engineBase = newBase;
		
		this.nbAgents = new JTextField(String.valueOf(newBase.getNbAgents()));
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
	public void mouseEntered(MouseEvent arg0) {
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setBorder(null);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(this.engineBase.isAPlayersBase()){
			this.setOpaque(true);
			this.setBackground(new Color(255, 100, 100));
		}

		if(BaseSprite.startingPoint == null && this.engineBase.isAPlayersBase()) {
			BaseSprite.startingPoint = this.engineBase;
		}
		if (BaseSprite.startingPoint != null && BaseSprite.startingPoint != this.engineBase) {
			BaseSprite.endingPoint = this.engineBase;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setOpaque(false);
		this.setBackground(null); // i don't know why i need to indicate a new background color...
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	public static void resetStartingPoint() {
		BaseSprite.startingPoint = null;
	}
	
	public static void resetEndingPoint() {
		BaseSprite.endingPoint = null;
	}
	
	// GETTERS & SETTERS
	
	public Base getEngineBase() {
		return this.engineBase;
	}
	
	public JTextField getNbAgents() {
		return this.nbAgents;
	}
	
	public static Point2D.Float getStartingPoint() {
		return BaseSprite.startingPoint.getCenter();
	}
	
	public static Point2D.Float getEndingPoint() {
		return BaseSprite.endingPoint.getCenter();
	}
	
	public static Base getStartingBase() {
		return BaseSprite.startingPoint;
	}
	
	public static Base getEndingBase() {
		return BaseSprite.endingPoint;
	}

	public static boolean isAStartingPoint() {
		return BaseSprite.startingPoint == null ? false : true;
	}
	
	public static boolean isAnEndingPoint() {
		return BaseSprite.endingPoint == null ? false : true;
	}
}
