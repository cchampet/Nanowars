package dispatcher;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import playable.Player;
import playable.TypeOfPlayer;
import renderer.BaseSprite;
import renderer.Renderer;
import engine.Base;
import engine.Engine;
import engine.Unit;

/**
 * This class represents the link between the engine, the renderer, and the IHM of the player.
 * @author Yuki
 *
 */
public class Dispatcher extends Thread{
	private static final int FRAME_RATE =  60;
	private static final int MILLISECOND_PER_FRAME =  1000 / FRAME_RATE;
	private static long nbFrame = 0;
	
	private static final int MAP_SCALE =  5;
	
	private static final Engine Engine = new Engine();
	private static final Renderer Renderer = new Renderer("Nano WAAAARS!!!");
	
	private static final HashMap<String, Player> Players = new HashMap<String, Player>(3);
	
	/**
	 * This method load the map from a datamap image.
	 * 
	 * @param filepath path of the datamap grey-scale image
	 * @param players 
	 * @throws IOException
	 */
	public static void loadMap(String filepath) throws IOException{
		BufferedImage map = ImageIO.read(new File(filepath));
		Raster mapData = map.getData();
		
		//For each pixels
		for(int y=0;y<map.getHeight();++y){
			for(int x=0;x<map.getWidth();++x){
				//if the pixel is not black : add a base
				Color color = new Color(map.getRGB(x, y));
				if(color.getRed() > 0 || color.getBlue() > 0 || color.getGreen() > 0){
					//choose the base
					//blue => a base for the player
					if(color.getBlue() > 127 && color.getRed() == 0 && color.getGreen() == 0){
						float pixelBlue = mapData.getSampleFloat(x, y, 2);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelBlue/255.)), Players.get("Player"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
					//red => a base for the IA
					else if(color.getRed() > 127  && color.getBlue() == 0 && color.getGreen() == 0){
						float pixelRed = mapData.getSampleFloat(x, y, 0);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelRed/255.)), Players.get("IA"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
					//white => a neutral base
					else{
						float pixelRed = mapData.getSampleFloat(x, y, 0);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelRed/255.)), Players.get("Neutral"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
				}
			}
		}
	}
	
	/**
	 * @param args input arguments
	 * @throws IOException 
	 */
	public static void main(String[] args){
		//init players
		Players.put("Player", new Player("Clement", TypeOfPlayer.PLAYER));
		Players.put("IA", new Player("IA_1", TypeOfPlayer.IA));
		Players.put("Neutral", new Player("Neutral", TypeOfPlayer.NEUTRAL));
		try {
			Players.get("Player").init("./tex/basePlayer.png", "./tex/unitPlayer.png");
			Players.get("IA").init("./tex/baseIA.png", "./tex/unitIA.png");
			Players.get("Neutral").init("./tex/baseNeutral.png");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		//init the renderer
		try {
			Dispatcher.Renderer.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//load the map
		try {
			Dispatcher.loadMap("./tex/datamap_v2.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//display the renderer
		Dispatcher.Renderer.render();
		
		//start the game
		//=>what we have to do in each frame
		ArrayList<Integer> idDeleted = new ArrayList<Integer>();
		while(true) {
			long begin = System.currentTimeMillis();
			
			Dispatcher.nbFrame = Dispatcher.nbFrame + 1;
			
			//work of the engine
			idDeleted = Dispatcher.Engine.doATurnGame();
			//work of the renderer
			Dispatcher.Renderer.refreshView(idDeleted);
			
			//work of the dispatcher : manage interaction between players and the engine
			
			//create units
			if(BaseSprite.isAStartingPoint() && BaseSprite.isAnEndingPoint()) {
				double nbAgentsOfUnitSent = BaseSprite.getStartingBase().getNbAgents() / 2; // agents of the unit = 50% of agents in the base
				Point2D.Float startingPoint = new Point2D.Float(BaseSprite.getStartingPoint().x + ((float)nbAgentsOfUnitSent / 2), BaseSprite.getStartingPoint().y + ((float)nbAgentsOfUnitSent / 2));
						
				Unit newUnit = new Unit(nbAgentsOfUnitSent, startingPoint, BaseSprite.getEndingPoint(), BaseSprite.getEndingBase(), Players.get("Player")); //for this moment, 3 = player
				newUnit.setId(Renderer.addUnitSprite(newUnit));
				Engine.addUnit(newUnit);
				
				BaseSprite.getStartingBase().reduceNbAgents(nbAgentsOfUnitSent);
				
				BaseSprite.resetEndingPoint();
			}
			
			//change the owner of bases
			for(Base b:Engine.getBases()){
				if(b.getNbAgents() == 0){
					Renderer.getSprite(b.getId()).setImage(Players.get("Neutral").getImageOfBase());
					Renderer.getSprite(b.getId()).repaint();
				}
				else if(b.getNbAgents() < 0){
					b.makeTheChangeOfCamp();
					Renderer.getSprite(b.getId()).setImage(b.getOwner().getImageOfBase());
					Renderer.getSprite(b.getId()).repaint();
				}
			}
			
			long end = System.currentTimeMillis();
			// wait if it's too fast, we need to wait 
			if ((end - begin) < Dispatcher.MILLISECOND_PER_FRAME) {
				try {
					Dispatcher.sleep(Dispatcher.MILLISECOND_PER_FRAME - (end - begin));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// GETTERS & SETTERS
	
	public static Engine getEngine() {
		return Dispatcher.Engine;
	}
	
	public static Renderer getRenderer() {
		return Dispatcher.Renderer;
	}

	public static HashMap<String, Player> getPlayers() {
		return Dispatcher.Players;
	}
}
