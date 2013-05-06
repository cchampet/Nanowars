package renderer;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
public class Renderer{
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
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DISPOSE ON CLOSE
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setResizable(false);
		//this.frame.setLayout(null);
		
		//Set up some images for the map
		this.mapRenderer.init();
	}
	
	/**
	 * Display the main frame.
	 */
	public void render(){
		this.frame.setVisible(true);
	}
	
	/**
	 * This method is called every frame, in order to refresh all important element in our frame.
	 * @param idDeletedInEngine this ArrayList<Integer> contains all id of engine elements just deleted.
	 */
	public void refreshView(ArrayList<Integer> idDeletedInEngine) {
		//update sprites
		Iterator<Sprite> iterSprites = this.getSprites().iterator();
		while(iterSprites.hasNext()){
			Sprite sprite = iterSprites.next();
			if(idDeletedInEngine.contains(sprite.getId())){
				sprite.setVisible(false);
				this.mapRenderer.getContainer().remove(sprite);
				iterSprites.remove();
			}
		}
		
		//update the display of nbAgents for each base
		for(BaseSprite baseSprite:this.getBaseSprites()){
			baseSprite.getNbAgents().setText(String.valueOf(baseSprite.getEngineBase().getNbAgents()));
		}
		
		//update the position of each unit
		for(UnitSprite unitSprite:this.getUnitSprites()){
			Point newPoint = new Point((int)unitSprite.getEngineUnit().getPosition().x, (int)unitSprite.getEngineUnit().getPosition().y);
			unitSprite.setLocation(newPoint);
		}
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
	
	//GETTERS & SETTERS
	
	/**
	 * Get the sprite by knowledge of his id.
	 * @param id : the id of the sprite.
	 * @return a sprite
	 */
	public Sprite getSprite(int id) {
		return this.mapRenderer.getSprite(id);
	}

	/**
	 * Get all the sprites.
	 * @return ArrayList<Sprite>
	 */
	public ArrayList<Sprite> getSprites() {
		return this.mapRenderer.getSprites();
	}

	/**
	 * Get only the unitSprites
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<UnitSprite> getUnitSprites() {
		return this.mapRenderer.getUnitSprites();
	}
	
	/**
	 * Get only the baseSprites
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<BaseSprite> getBaseSprites() {
		return this.mapRenderer.getBaseSprites();
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
