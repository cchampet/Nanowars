package renderer;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import playable.TypeOfPlayer;

import engine.Base;
import engine.Tower;
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
	private UIRenderer uiRenderer;
		
	/**
	 * Constructor asking for the window title.
	 * @param frameTitle - String containing the title of the game.
	 */
	public Renderer(String frameTitle){
		super();
		this.frame = new JFrame(frameTitle);
		this.mapRenderer = new MapRenderer(this.frame.getLayeredPane(), WIDTH, HEIGHT);
		this.uiRenderer = new UIRenderer(this.frame.getLayeredPane(), WIDTH, HEIGHT);
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
		this.frame.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 32){ //key "space"
					BaseSprite.resetStartingBase();
					BaseSprite.resetEndingBase();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
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
		//update sprite list
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
		
		//update the sprite depends on the owner, for each base
		for(BaseSprite baseSprite:this.getBaseSprites()){
			baseSprite.setImage(baseSprite.getEngineBase().getOwner().getType().getImageOfBase());
		}
		
		//update the position of each unit
		for(UnitSprite unitSprite:this.getUnitSprites()){
			Point newPoint = new Point((int)(unitSprite.getEngineUnit().getPosition().x - unitSprite.getSpriteSize()/2), (int)(unitSprite.getEngineUnit().getPosition().y - unitSprite.getSpriteSize()/2));
			unitSprite.setLocation(newPoint);
		}
		
		//update the sprite depends on the owner of the associated base, for each tower
		for(TowerSprite towerSprite:this.getTowerSprites()){
			if(towerSprite.getEngineTower().getLevel() != 0)
				towerSprite.setImage(towerSprite.getEngineTower().getAssociatedBase().getOwner().getType().getImageOfTower());
			else
				towerSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfTower());	
		}
		
		//update the display of nbAgents and of the level for each tower
		for(TowerSprite towerSprite:this.getTowerSprites()){
			towerSprite.getNbAgents().setText(String.valueOf(towerSprite.getEngineTower().getNbAgents()));
			towerSprite.getLevel().setText("lvl "+String.valueOf(towerSprite.getEngineTower().getLevel()));
		}
	}
	
	//MAPRENDERER INDIRECTIONS
	
	/**
	 * Add the Base Sprite to the MapRenderer Sprite Collection
	 * @param newBase : the base engine, corresponding to the next created sprite.
	 */
	public int addBaseSprite(Base newBase){
		return this.mapRenderer.addBaseSprite(newBase);
	}
	
	/**
	 * Add the Unit Sprite to the MapRenderer Sprite Collection
	 * @param newUnit : the unit engine, corresponding to the next created sprite.
	 */
	public int addUnitSprite(Unit newUnit){
		return this.mapRenderer.addUnitSprite(newUnit);
	}
	
	/**
	 * Add the Tower Sprite to the MapRenderer Sprite Collection
	 * @param newTower : the tower engine, corresponding to the next created sprite.
	 */
	public int addTowerSprite(Tower newTower){
		return this.mapRenderer.addTowerSprite(newTower);
	}
	
	//UIRENDERER INDIRECTIONS
	
	/**
	 * Display winner message
	 */
	public void displayWinner() throws IOException {
		//Set up the frame
		this.uiRenderer.displayWinner();
	}
	
	/**
	 * Display a radial menu to choose how many units send 
	 * @param mousePosition Position of the mouse, where the radial menu will appear
	 * @throws IOException
	 */
	public void displayRadialMenuMovment(Point mousePosition) throws IOException{
		this.uiRenderer.displayRadialMenuMovment(mousePosition);
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
	
	/**
	 * Get only the towersSprites
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<TowerSprite> getTowerSprites() {
		return this.mapRenderer.getTowerSprites();
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
