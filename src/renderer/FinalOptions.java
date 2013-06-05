package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import playable.TypeOfPlayer;

public class FinalOptions extends Sprite implements MouseListener{
	/**
	 * isSelected is a boolean that warns if the option is selected by the player.
	 */
	private boolean isSelected;
	/**
	 * pathOfTheOption is the path of the image corresponding to this option / WIN -> Next lvl / LOSE -> Retry.
	 */
	
	private String pathOfTheOption;
	/**
	 * title is the JTextField that is used to display the name of the level.
	 */
	private JTextField title;
	
	// "@TO DO": Something different will be needed to go back to the menu -> clear the window, display the menu / stop/restart threads?
	// Bug: The circles containing the options are continuously displayed after the 1st game -> renderer/threads
	
	public FinalOptions(String pathOfTheOption, String nameOfTheOption){
		super();
		
		this.isSelected = false;
		this.pathOfTheOption = pathOfTheOption;
		
		this.setLayout(new BorderLayout());
		
		this.title = new JTextField(nameOfTheOption);
		this.title.setPreferredSize(new Dimension(23, 20));
		this.title.setDisabledTextColor(new Color(255, 255, 255));
		this.title.setEnabled(false);
		this.title.setBorder(null);
		this.title.setHorizontalAlignment(JTextField.CENTER);
		this.title.setOpaque(false);
		this.title.setIgnoreRepaint(false); // for better performances
		this.title.addMouseListener(this);
		this.add(this.title, BorderLayout.CENTER);
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
		this.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	// GETTERS
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public String getPathOfTheOption(){
		return pathOfTheOption;
	}

}