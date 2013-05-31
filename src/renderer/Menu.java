package renderer;

import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Menu extends JLabel implements MouseListener {
	
	private int width;
	private int height;
	private boolean gameNotBegun;


	public Menu(int width, int height){
		super();
						
		this.height = height;
		this.width = width;
		this.gameNotBegun = true;
		
		this.addMouseListener(this);
	}
	
	public void init() throws IOException{
		//Load the menu background image
		ImageIcon bgMenuImage = new ImageIcon("./tex/youWin.png");
		if(bgMenuImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		this.setBounds(0, 0, this.width, this.height);
		this.setIcon(bgMenuImage);		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.gameNotBegun = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	// GETTERS

	public boolean isGameNotBegun() {
		return gameNotBegun;
	}
}