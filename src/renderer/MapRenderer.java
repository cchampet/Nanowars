package renderer;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import dispatcher.Dispatcher;

import playable.TypeOfPlayer;
import engine.Base;
import engine.Tower;
import engine.Unit;


/**
 * MapRenderer is the specific renderer which manage the displaying of the pure game elements, like background, bases and agents.
 * @author Jijidici
 *
 */
public class MapRenderer implements MouseListener{
	private final int BACKGROUND_LAYER = 0;
	private final int BASE_LAYER = 10;
	private final int UNIT_LAYER = 100;
	private final int EFFECT_LAYER = 150;

	/**
	 * background : the background layer. No event connected on it. 
	 */
	private JLabel background;

	/**
	 * effectsLayer : a transparent layer, above the background layer. We connect events on it.
	 */
	private JLabel effectsLayer;

	private Container container;
	private int width;
	private int height;
	private final ArrayList<Sprite> sprites;
	/**
	 * 
	 */
	private static Point2D.Float selectionStartingCorner;
	private static Point2D.Float selectionEndingCorner;
	private static boolean mouseDown = false;
	//private boolean isRunning = false;
	
	/**
	 * Constructor which asking the frame container in which the game elements will be rendered. Il also ask the frame dimensions
	 * @param c main parent container
	 * @param width window width
	 * @param height window height
	 */
	@SuppressWarnings("serial")
	public MapRenderer(Container c, int width, int height){
		super();
		
		this.background = new JLabel();
		this.container = c;
		this.width = width;
		this.height = height;
		this.sprites = new ArrayList<Sprite>(5);
		this.effectsLayer = new JLabel() {
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
				this.setVisible(false);
				// draw the line between bases
				if(BaseSprite.isThereAStartingBase()){
					g.setColor(Color.WHITE);
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePosition, this);
					for(Base startingBase:BaseSprite.getStartingBases()){
						g.drawLine((int)startingBase.getCenter().getX(), (int)startingBase.getCenter().getY(), 
							mousePosition.x, mousePosition.y);
					}
				}
				if(MapRenderer.mouseDown==true){
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePosition, this);
					g.drawRect(
						Math.min(
							(int)MapRenderer.selectionStartingCorner.x,
							mousePosition.x 
						),	
						Math.min(	
							(int)MapRenderer.selectionStartingCorner.y,
							mousePosition.y 
						), 
						Math.abs(
							mousePosition.x 
							- (int)MapRenderer.selectionStartingCorner.x
						), 
						Math.abs(	
							mousePosition.y
							- (int)MapRenderer.selectionStartingCorner.y
						)
					);
				}
				
				this.setVisible(true);
			}
		};

		MapRenderer.selectionStartingCorner = new Point2D.Float();
		MapRenderer.selectionEndingCorner = new Point2D.Float();

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
		
		this.effectsLayer.setBounds(0, 0, this.width, this.height);
		this.container.add(this.effectsLayer, new Integer(EFFECT_LAYER));
		
		//Manage events
		this.background.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Dispatcher.getRenderer().hideRadialMenuMovment();
				BaseSprite.resetStartingBase();
				BaseSprite.resetEndingBase();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		this.effectsLayer.addMouseListener(this);
	}
	
	/**
	 * Add a Base to the Sprite collection in the renderer.
	 * @param newBase : the base engine, corresponding to the next created sprite.
	 */
	public int addBaseSprite(Base newBase){
		BaseSprite newSprite = new BaseSprite(newBase);
		newSprite.setSize(newBase.getCapacity());
		//set the image of the base
		if(newBase.isNeutral())
			newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		else if(newBase.getOwner().getType() == TypeOfPlayer.IA_1)
			newSprite.setImage(TypeOfPlayer.IA_1.getImageOfBase());
		else if(newBase.getOwner().getType() == TypeOfPlayer.IA_2)
			newSprite.setImage(TypeOfPlayer.IA_2.getImageOfBase());
		else if(newBase.getOwner().getType() == TypeOfPlayer.PLAYER)
			newSprite.setImage(TypeOfPlayer.PLAYER.getImageOfBase());
		else
			newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(newBase.getXCoord(), newBase.getYCoord(), newBase.getCapacity(), newBase.getCapacity());
		container.add(newSprite, new Integer(BASE_LAYER));
		sprites.add(newSprite);
		return newSprite.getId();
	}
	
	/**
	 * Add a unit to the Sprite collection in the renderer.
	 * @param newUnit : the unit engine, corresponding to the next created sprite.
	 */
	public int addUnitSprite(Unit newUnit){
		UnitSprite newSprite = new UnitSprite(newUnit);
		newSprite.setSize((int) newUnit.getNbAgents());
		//set the image of the unit
		if(newUnit.getOwner().getType() == TypeOfPlayer.PLAYER)
			newSprite.setImage(TypeOfPlayer.PLAYER.getImageOfUnit());
		else if(newUnit.getOwner().getType() == TypeOfPlayer.IA_1)
			newSprite.setImage(TypeOfPlayer.IA_1.getImageOfUnit());
		else if(newUnit.getOwner().getType() == TypeOfPlayer.IA_2)
			newSprite.setImage(TypeOfPlayer.IA_2.getImageOfUnit());
		
		newSprite.setBounds((int)(newUnit.getPosition().x - newSprite.getSpriteSize()/2), (int)(newUnit.getPosition().y - newSprite.getSpriteSize()/2), newSprite.getSpriteSize(), newSprite.getSpriteSize());

		container.add(newSprite, new Integer(UNIT_LAYER));
		sprites.add(newSprite);
		return newSprite.getId();
	}
	
	/**
	 * Add a tower to the Sprite collection in the renderer.
	 * @param newTower : the tower engine, corresponding to the next created sprite.
	 */
	public int addTowerSprite(Tower newTower){
		TowerSprite newSprite = new TowerSprite(newTower);
		//set the image of the tower
		newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfTowerLvl0());
		newSprite.setBounds((int)newTower.getPosition().x, (int)newTower.getPosition().y, newSprite.getSpriteSize(), newSprite.getSpriteSize());
		container.add(newSprite, new Integer(BASE_LAYER));
		sprites.add(newSprite);
		return newSprite.getId();
	}
	
	
	// GETTERS & SETTERS

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

	/**
	 * Get all the sprites.
	 * @return ArrayList<Sprite>
	 */
	public ArrayList<Sprite> getSprites() {
		return this.sprites;
	}

	/**
	 * Get only the unitSprites.
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<UnitSprite> getUnitSprites() {
		ArrayList<UnitSprite> res = new ArrayList<UnitSprite>();
		for(Sprite sprite:this.sprites){
			if(sprite.getClass() == UnitSprite.class){
				res.add((UnitSprite) sprite);
			}
		}
		return res;
	}

	/**
	 * Get only the baseSprites.
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<BaseSprite> getBaseSprites() {
		ArrayList<BaseSprite> res = new ArrayList<BaseSprite>();
		for(Sprite sprite:this.sprites){
			if(sprite.getClass() == BaseSprite.class){
				res.add((BaseSprite) sprite);
			}
		}
		return res;
	}

	/**
	 * Get only the towerSprites.
	 * @return ArrayList<UnitSprite>
	 */
	public ArrayList<TowerSprite> getTowerSprites() {
		ArrayList<TowerSprite> res = new ArrayList<TowerSprite>();
		for(Sprite sprite:this.sprites){
			if(sprite.getClass() == TowerSprite.class){
				res.add((TowerSprite) sprite);
			}
		}
		return res;
	}
	
	/**
	 * @return the container
	 */
	public Container getContainer() {
		return container;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		BaseSprite.resetStartingBase();
		BaseSprite.resetEndingBase();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseDown = true;
        //initThread();
		if(MapRenderer.selectionStartingCorner==null){
			MapRenderer.selectionStartingCorner=new Point2D.Float();
		}
		MapRenderer.selectionStartingCorner.x=(float)arg0.getX();
		MapRenderer.selectionStartingCorner.y=(float)arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseDown = false;
		if(selectionStartingCorner != null){
			if(MapRenderer.selectionEndingCorner==null){
				MapRenderer.selectionEndingCorner=new Point2D.Float();
			}
			MapRenderer.selectionEndingCorner.x=arg0.getX();
			MapRenderer.selectionEndingCorner.y=arg0.getY();
			BaseSprite.setStartingBases(selectionStartingCorner,selectionEndingCorner);
		}
		
	}
	
	public static Point2D.Float getSelectionStartingCorner() {
		return selectionStartingCorner;
	}

	public static Point2D.Float getSelectionEndingCorner() {
		return selectionEndingCorner;
	}
}
