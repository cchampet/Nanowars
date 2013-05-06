package renderer;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import playable.TypeOfPlayer;
import engine.Base;
import engine.Unit;


/**
 * MapRenderer is the specific renderer which manage the displaying of the pure game elements, like background, bases and agents.
 * @author Jijidici
 *
 */
public class MapRenderer{
	private final int BACKGROUND_LAYER = 0;
	private final int GAME_LAYER = 100;

	private JLabel background;
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
	@SuppressWarnings("serial")
	public MapRenderer(Container c, int width, int height){
		super();

		this.background = new JLabel() {
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
				// draw the line between bases
				if(BaseSprite.isAStartingPoint()){
					g.setColor(Color.WHITE);
					g.drawLine((int)BaseSprite.getStartingPoint().x, (int)BaseSprite.getStartingPoint().y, 
							MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
				}
			}
		};
		this.container = c;
		this.width = width;
		this.height = height;
		this.sprites = new ArrayList<Sprite>(5);
		
		//Manage events
		this.background.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				BaseSprite.resetStartingPoint();
				BaseSprite.resetEndingPoint();
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
	}
	
	/**
	 * Add a Base to the Sprite collection in the renderer.
	 * @param newBase : the engine base, corresponding to the next created sprite.
	 */
	public int addBaseSprite(Base newBase){
		BaseSprite newSprite = new BaseSprite(newBase);
		newSprite.setSize(newBase.getCapacity());
		//set the image of the base
		if(newBase.isNeutral())
			newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		else if(newBase.getOwner().getType() == TypeOfPlayer.IA)
			newSprite.setImage(TypeOfPlayer.IA.getImageOfBase());
		else if(newBase.getOwner().getType() == TypeOfPlayer.PLAYER)
			newSprite.setImage(TypeOfPlayer.PLAYER.getImageOfBase());
		else
			newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(newBase.getXCoord(), newBase.getYCoord(), newBase.getCapacity(), newBase.getCapacity());
		container.add(newSprite, new Integer(GAME_LAYER));
		sprites.add(newSprite);
		return newSprite.getId();
	}
	
	/**
	 * Add a unit to the Sprite collection in the renderer.
	 * @param newUnit : the engine unit, corresponding to the next created sprite.
	 */
	public int addUnitSprite(Unit newUnit){
		UnitSprite newSprite = new UnitSprite(newUnit);
		newSprite.setSize((int) newUnit.getNbAgents());
		newSprite.setImage(TypeOfPlayer.PLAYER.getImageOfUnit());
		newSprite.setBounds((int)newUnit.getPosition().x, (int)newUnit.getPosition().y, (int)newUnit.getNbAgents(), (int)newUnit.getNbAgents());
		container.add(newSprite, new Integer(GAME_LAYER));
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
	 * @return the container
	 */
	public Container getContainer() {
		return container;
	}
}
