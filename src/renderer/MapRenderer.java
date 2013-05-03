package renderer;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import engine.Base;
import engine.Unit;


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
	private BufferedImage unitImage;
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
					g.setColor(Color.GREEN);
					
					/*System.out.println("MouseInfo.getPointerInfo().getLocation().x : " + MouseInfo.getPointerInfo().getLocation().x);
					System.out.println("MouseInfo.getPointerInfo().getLocation().y : " + MouseInfo.getPointerInfo().getLocation().y);
					System.out.println("BaseSprite.getStartingPoint().x : " + BaseSprite.getStartingPoint().x);
					System.out.println("BaseSprite.getStartingPoint().y : " + BaseSprite.getStartingPoint().y);
					*/
					g.drawLine((int)BaseSprite.getStartingPoint().x, (int)BaseSprite.getStartingPoint().y, 
							MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
				}
			}
		};
		this.container = c;
		this.width = width;
		this.height = height;
		this.sprites = new ArrayList<Sprite>();
		
		//Manage events
		this.background.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Reset startingPoint and endingPoint");
				BaseSprite.resetStartingPoint();
				BaseSprite.resetEndingPoint();
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
	 * Add a unit to the Sprite collection in the renderer.
	 * @param newUnit : the engine unit, corresponding to the next created sprite.
	 */
	public int addUnitSprite(Unit newUnit){
		UnitSprite newSprite = new UnitSprite(newUnit);
		newSprite.setSize(newUnit.getNbAgents());
		newSprite.setImage(unitImage);
		newSprite.setBounds((int)newUnit.getPosition().x, (int)newUnit.getPosition().y, newUnit.getNbAgents(), newUnit.getNbAgents());
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
		this.unitImage = ImageIO.read(new File("./tex/unit.png"));
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

	public ArrayList<Sprite> getSprites() {
		return this.sprites;
	}

	public ArrayList<UnitSprite> getUnitSprites() {
		ArrayList<UnitSprite> res = new ArrayList<UnitSprite>();
		for(Sprite sprite:this.sprites){
			if(sprite.getClass() == UnitSprite.class){
				res.add((UnitSprite) sprite);
				System.out.println("unit !");
			}
		}
		return res;
	}
}
