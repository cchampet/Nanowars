package renderer;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import engine.Base;
import engine.Unit;


/**
 * Renderer is the main class for all displaying stuff. It creates all specific renderers like MapRenderer.
 * It also manage the main frame.
 * 
 * @author Jijidici
 *
 */

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private JFrame frame;
	private MapRenderer mapRenderer;
		
	/**
	 * Constructor asking for the window title.
	 * @param frameTitle - String containing the title of the game.
	 */
	public Renderer(String frameTitle){
		super();
		this.frame = new JFrame(frameTitle);
		this.mapRenderer = new MapRenderer(this.frame.getLayeredPane(), WIDTH, HEIGHT);
	}
	
	/**
	 * Initialize the Renderer by building every Swing resource and setting the main window.
	 * @throws IOException
	 */
	public void init() throws IOException{
		//Set up the frame
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setResizable(false);
		//this.frame.setLayout(null);
		
		//Set up the background image
		this.mapRenderer.init();
	}
	
	/**
	 * Display the main frame.
	 */
	public void render(){
		this.frame.setVisible(true);
	}
	
	/**
	 * Add the Base Sprite to the MapRenderer Sprite Collection
	 * @param newBase : the base engine, corresponding to the next created sprite.
	 */
	public int addBaseSprite(Base newBase){
		return this.mapRenderer.addBaseSprite(newBase);
	}
	
	/**
	 * Add the Unit Sprite to the MapRenderer Sprite Collection
	 * @param Unewnit : the unit engine, corresponding to the next created sprite.
	 */
	public int addUnitSprite(Unit newUnit){
		return this.mapRenderer.addUnitSprite(newUnit);
	}
	
	/**
	 * Get the id of a sprite at a specific index in the list of sprites.
	 * @param index : the index of the sprite in the list of sprites.
	 * @return an id
	 */
	public int getSpriteIDAt(int index){
		return this.mapRenderer.getSpriteIDAt(index);
	}
	
	/**
	 * Get the sprite by knowledge of his id.
	 * @param id : the id of the sprite.
	 * @return a sprite
	 */
	public Sprite getSprite(int id) {
		return this.mapRenderer.getSprite(id);
	}

	public ArrayList<Sprite> getSprites() {
		return this.mapRenderer.getSprites();
	}

	public ArrayList<UnitSprite> getUnitSprites() {
		return this.mapRenderer.getUnitSprites();
	}
}
