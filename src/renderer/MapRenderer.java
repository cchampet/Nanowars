package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import engine.Base;

/**
 * MapRenderer is the specific renderer which manage the displaying of the pure game elements, like background, bases and agents.
 * @author Jijidici
 *
 */
public class MapRenderer {
	private final int BACKGROUND_LAYER = 0;
	private final int GAME_LAYER = 100;
	
	private JLabel background;
	private Container c;
	private int width;
	private int height;
	
	/**
	 * Constructor which asking the frame container in which the game elements will be rendered. Il also ask the frame dimensions
	 * @param c main parent container
	 * @param width window width
	 * @param height windo height
	 */
	public MapRenderer(Container c, int width, int height){
		super();
		this.background = new JLabel();
		this.c = c;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Add a Base to the rendering process.
	 * @param b base to render
	 */
	public void renderABase(Base b){
		c.add(b.getSprite(), new Integer(GAME_LAYER));
	}
	
	/**
	 * Initialize the MapRenderer by building every Swing resource and loading every image.
	 * @throws IOException
	 */
	public void init() throws IOException{
		//Load the background image
		ImageIcon bgImage = new ImageIcon("./tex/background.jpg");
		if(bgImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}		
		this.background.setBounds(0, 0, this.width, this.height);
		this.background.setIcon(bgImage);
		c.add(this.background, new Integer(BACKGROUND_LAYER));
	}
}
