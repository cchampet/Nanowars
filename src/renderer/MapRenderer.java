package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapRenderer {
	private JLabel background;
	private Container c;
	private int width;
	private int height;
	
	public MapRenderer(Container c, int width, int height){
		super();
		this.background = new JLabel();
		this.c = c;
		this.width = width;
		this.height = height;
	}
	
	public void init() throws IOException{
		//Load the background image
		ImageIcon bgImage = new ImageIcon("./tex/background.jpg");
		if(bgImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		
		this.background.setBounds(0, 0, this.width, this.height);
		this.background.setIcon(bgImage);
		c.add(this.background);
	}
}
