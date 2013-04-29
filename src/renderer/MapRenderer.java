package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private BufferedImage baseImage;
	private Container container;
	private int width;
	private int height;
	private final ArrayList<Sprite> sprites;
	
	/**
	 * Constructor which asking the frame container in which the game elements will be rendered. Il also ask the frame dimensions
	 * @param c main parent container
	 * @param width window width
	 * @param height window height
	 */
	public MapRenderer(Container c, int width, int height){
		super();
		this.background = new JLabel();
		this.baseImage = null;
		this.container = c;
		this.width = width;
		this.height = height;
		this.sprites = new ArrayList<Sprite>();
	}
	
	/**
	 * Add a Base to the Sprite collection in the renderer.
	 * @param newBase : the engine base, corresponding to the next created sprite.
	 */
	public int addBaseSprite(Base newBase){
		BaseSprite newSprite = new BaseSprite(newBase);
		newSprite.setSize(newBase.getCapacity());
		newSprite.setImage(baseImage);
		newSprite.setBounds(newBase.getXCoord(), newBase.getYCoord(), newBase.getCapacity(), newBase.getCapacity());
		container.add(newSprite, new Integer(GAME_LAYER));
		sprites.add(newSprite);
		return newSprite.getId();
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
		this.container.add(this.background, new Integer(BACKGROUND_LAYER));
		
		//Load the base image
		this.baseImage = ImageIO.read(new File("./tex/base.png"));
	}
	
	/**
	 * Get the id of a sprite at a specific index in the list of sprites.
	 * @param index : the index of the sprite in the list of sprites.
	 */
	public int getSpriteIDAt(int index){
		return this.sprites.get(index).getId();
	}

	/**
	 * Get the sprite by knowledge of his id.
	 * @param id : the id of the sprite.
	 */
	public Sprite getSprite(int id) {
		for(Sprite sprite : this.sprites) {
			if(sprite.getId() == id)
				return sprite;
		}
		return null;
	}
}
