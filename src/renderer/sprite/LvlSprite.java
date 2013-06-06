package renderer.sprite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

import playable.TypeOfPlayer;

@SuppressWarnings("serial")
public class LvlSprite extends Sprite implements MouseListener{
	/**
	 * isSelected is a boolean which warn if the level is selected by the player.
	 */
	private boolean isSelected;
	/**
	 * nameOfTheLevel is the path of the image corresponding to this level.
	 */
	private String pathOfTheLevel;
	/**
	 * numLvl is the number of the level.
	 */
	private final int numLvl;
	/**
	 * lvl is the JTextField which is used to display the name of the level.
	 */
	private JTextField lvl;
	/**
	 * Image of the background of the level
	 */
	private ImageIcon backgroundImage;
	
	public LvlSprite(String pathOfTheLevel, String pathOfTheBackground, int numOfTheLevel) throws IOException{
		super();
		
		this.isSelected = false;
		this.pathOfTheLevel = pathOfTheLevel;
		this.numLvl = numOfTheLevel;
		
		this.setLayout(new BorderLayout());
		
		this.lvl = new JTextField(String.valueOf(numOfTheLevel));
		this.lvl.setPreferredSize(new Dimension(23, 20));
		this.lvl.setDisabledTextColor(new Color(255, 255, 255));
		this.lvl.setEnabled(false);
		this.lvl.setBorder(null);
		this.lvl.setHorizontalAlignment(JTextField.CENTER);
		this.lvl.setOpaque(false);
		this.lvl.setIgnoreRepaint(false); // for better performance
		this.lvl.addMouseListener(this);
		this.add(this.lvl, BorderLayout.CENTER);
		
		this.backgroundImage = new ImageIcon(pathOfTheBackground);
		if(this.backgroundImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
	}
	
	public void changeTheNameOfTheLvlDisplay(String newName){
		this.lvl.setText(newName);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.isSelected = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setImage(TypeOfPlayer.PLAYER.getImageOfBase());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	// GETTERS
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public void resetIsSelected(){
		this.isSelected = false;
	}
	
	public String getPathOfTheLevel(){
		return pathOfTheLevel;
	}
	

	public ImageIcon getBackgroundImage(){
		return this.backgroundImage;
	}
	
	public int getNumLvl(){
		return this.numLvl;
	}
}
