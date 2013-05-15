package renderer;


import java.awt.Container;
import java.awt.MediaTracker;
import java.awt.Point;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class UIRenderer {

	private final int UI_LAYER = 200;
	private JLabel background;
	private Container container;
	private int width;
	private int height;
	
	public UIRenderer(Container c, int width, int height){
		super();
		this.background = new JLabel();
		this.container = c;
		this.height=height;
		this.width=width;
	}
	
	/**
	 * Display a "WINNER" message when the player win and before exit program
	 * @throws IOException
	 */
	public void displayWinner() throws IOException{
		//Load the background image
		ImageIcon bgImage = new ImageIcon("./tex/youWin.png");
		if(bgImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}		
		this.background.setBounds(0, 0, this.width, this.height);
		this.background.setIcon(bgImage);
		this.container.add(this.background, new Integer(UI_LAYER));
	}
	
	/**
	 * Display a radial menu to choose how many units send 
	 * @param mousePosition Position of the mouse, where the radial menu will appear
	 * @throws IOException
	 */
	public void displayRadialMenuMovment(Point mousePosition) throws IOException{
		//Load the menu image
		ImageIcon rmImage = new ImageIcon("./tex/radialmenu_movment.png");
		if(rmImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		
		SwingUtilities.convertPointFromScreen(mousePosition, this.container);
		
		JLabel radialMenu = new JLabel(rmImage);
		radialMenu.setBounds(mousePosition.x - rmImage.getIconWidth()/2, mousePosition.y - rmImage.getIconHeight()/2, rmImage.getIconWidth(), rmImage.getIconHeight());
		this.container.add(radialMenu, new Integer(UI_LAYER));
	}
}



