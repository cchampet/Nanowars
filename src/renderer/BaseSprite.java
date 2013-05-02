package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import engine.Base;

/**
 * This class display a base.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class BaseSprite extends Sprite implements MouseListener {
	/**
	 * nbAgents is the JTextField which is used to display the nbAgents of the correpsonding base.
	 */
	private JTextField nbAgents;

	public BaseSprite(Base newBase) {
		super();
		
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
	
	public JTextField getNbAgents() {
		return this.nbAgents;
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
		this.setOpaque(true);
		this.setBackground(new Color(255, 100, 100));
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setOpaque(false);
		this.setBackground(null); // i don't know why i need to indicate a new background color...
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
}
