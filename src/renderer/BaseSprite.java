package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
	 * startingBase and endingBase are static variable, useful to decide in which direction the player sends units.
	 */
	private static ArrayList<Base> startingBases = new ArrayList<Base>();
	private static Base endingBase;
	private static Point2D.Float selectionCornerBegin;
	private static Point2D.Float selectionCornerEnd;
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding base.
	 */
	
	// Ce sera pas un mouse press mais un mouse release
	// L'envoi de units se fait dans BaseUnit
	// Dans la boucle principale le dispatcher regarde si il y a une base de départ et d'arrivée
	//1. CHECK Faire en sorte que tout ce qui marchait avant marche avec une liste de startingBases
	//2. Faire en sorte que lorsqu'on clique un rectangle transparent apparaisse
	//3. Faire en sorte que le bases comprises dans le domaine du rectangle soient selectionnées.
	
	private JTextField nbAgents;
	/**
	 * engineBase is a reference to the corresponding base of this sprite.
	 */
	private Base engineBase;

	public BaseSprite(Base newBase) {
		super();
		this.setLayout(new BorderLayout());
		
		this.engineBase = newBase;
		
		this.nbAgents = new JTextField(String.valueOf(newBase.getNbAgents()));
		this.nbAgents.setPreferredSize(new Dimension(23, 20));
		this.nbAgents.setDisabledTextColor(new Color(255, 255, 255));
		this.nbAgents.setEnabled(false);
		this.nbAgents.setBorder(null);
		this.nbAgents.setHorizontalAlignment(JTextField.CENTER);
		this.nbAgents.setOpaque(false);
		this.nbAgents.setIgnoreRepaint(false); // for better performance
		this.nbAgents.addMouseListener(this);
		this.add(this.nbAgents, BorderLayout.CENTER);
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
		if(this.engineBase.isAPlayerBase()){
			this.setOpaque(true);
			this.setBackground(new Color(255, 100, 100));
		}

		if(BaseSprite.startingBases.size() == 0 && this.engineBase.isAPlayerBase()) {
			BaseSprite.startingBases.add(this.engineBase);

		}
		// The following 'if' statement rewrites the statement :
		// if(BaseSprite.startingBases != null && BaseSprite.startingBase != this.engineBase)
		// in  accordance with the implementation of multiple starting bases.
		if (BaseSprite.startingBases.size() != 0 && !(BaseSprite.startingBases.size()==1 && BaseSprite.startingBases.contains(this.engineBase))) {
			BaseSprite.endingBase = this.engineBase;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setOpaque(false);
		this.setBackground(null); // i don't know why i need to indicate a new background color...
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	public static void resetStartingBase() {
		BaseSprite.startingBases.removeAll(startingBases);
	}
	
	public static void resetEndingBase() {
		BaseSprite.endingBase = null;
	}
	
	// GETTERS & SETTERS
	
	public Base getEngineBase() {
		return this.engineBase;
	}
	
	public JTextField getNbAgents() {
		return this.nbAgents;
	}
	
	public static Point2D.Float getStartingPoint() {
		if(BaseSprite.startingBases.size()>0){
			return BaseSprite.startingBases.get(0).getCenter();
		}
		else{
			return null;
		}
	}
	
	public static Point2D.Float getEndingPoint() {
		return BaseSprite.endingBase.getCenter();
	}
	
	public static Base getStartingBase() {
		if(BaseSprite.startingBases.size()>0){
			return BaseSprite.startingBases.get(0);
		}
		else{
			System.out.println("Pas de base de départ");
			return null;
		}
	}
	
	public static Base getEndingBase() {
		return BaseSprite.endingBase;
	}

	public static boolean isAStartingBase() {
			return BaseSprite.startingBases.size() == 0 ? false : true;
	}
	
	public static boolean isAnEndingBase() {
		return BaseSprite.endingBase == null ? false : true;
	}
}
