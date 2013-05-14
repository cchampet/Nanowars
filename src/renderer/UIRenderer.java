package renderer;


import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


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
	
}



