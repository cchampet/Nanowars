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

import dispatcher.Dispatcher;
import engine.Base;
import engine.Element;

/**
 * This class display a base.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class BaseSprite extends ElementSprite implements MouseListener{
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the corresponding base.
	 */
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

		if(!BaseSprite.isThereAtLeastOneStartingElement() && this.engineBase.isAPlayerBase())
			BaseSprite.startingElements.add(this.engineBase);

		if (BaseSprite.isThereAtLeastOneStartingElement() && !(BaseSprite.startingElements.size()==1 && BaseSprite.startingElements.contains(this.engineBase)))
			BaseSprite.endingElement = this.engineBase;

		//to fix the bug when you can control the IA
		if(!BaseSprite.startingElements.isEmpty()){
			boolean aSeletedBaseIsNotAPlayerBase = false;
			for(Element element:BaseSprite.startingElements){
				if(element.getClass() == Base.class){
					if(!((Base) element).isAPlayerBase())
						aSeletedBaseIsNotAPlayerBase = true;
				}
			}
			if(aSeletedBaseIsNotAPlayerBase){
				BaseSprite.resetStartingElements();
				BaseSprite.resetEndingElement();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setOpaque(false);
		this.setBackground(null); // i don't know why i need to indicate a new background color...
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	// GETTERS & SETTERS
	
	public Base getEngineBase() {
		return this.engineBase;
	}
	
	public JTextField getNbAgents() {
		return this.nbAgents;
	}
	
	public static ArrayList<Base> getStartingBases() {
		ArrayList<Base> res = new ArrayList<Base>();
		for(Element el:BaseSprite.startingElements){
			if(el.getClass() == Base.class)
				res.add((Base) el);
		}
		return res;
	}
	
	public static void setStartingBases(Point2D.Float startingCorner,Point2D.Float endingCorner){
		for(Base potentialStartingBase:Dispatcher.getEngine().getBases()){
			if(potentialStartingBase.getOwner().isPlayer()){	
				if(Math.min(startingCorner.x,endingCorner.x) < potentialStartingBase.getCenter().x 
					&&	potentialStartingBase.getCenter().x < Math.max(startingCorner.x,endingCorner.x)
					&&	Math.min(startingCorner.y,endingCorner.y) < potentialStartingBase.getCenter().y
					&&  potentialStartingBase.getCenter().y < Math.max(startingCorner.y,endingCorner.y)
				){
					BaseSprite.startingElements.add(potentialStartingBase);
				}
			}
		}
	}
}
