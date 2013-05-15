package dispatcher;

import java.awt.Color;
import java.awt.MouseInfo;
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
import renderer.TowerSprite;
import engine.Base;
import engine.Engine;
import engine.Tower;

/**
 * This class represents the link between the engine, the renderer, and the IHM of the player.
 * @author Yuki
 *
 */
public class Dispatcher {
	private static final int FRAME_RATE =  60;
	private static final int MILLISECOND_PER_FRAME =  1000 / FRAME_RATE;
	private static long nbFrame = 0;
	
	private static final int MAP_SCALE =  5;
	
	private static final Engine Engine = new Engine();
	private static final Renderer Renderer = new Renderer("Nano WAAAARS!!!");
	private static final HashMap<String, Player> Players = new HashMap<String, Player>();
		
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
		
		//For each pixels, create the bases
		for(int y=0;y<map.getHeight();++y){
			for(int x=0;x<map.getWidth();++x){
				Color color = new Color(map.getRGB(x, y));
				//if the pixel is not black
				if(color.getRed() > 0 || color.getBlue() > 0 || color.getGreen() > 0){
					//blue [50, 150] => a base for the player
					if(color.getBlue() >= 50 && color.getBlue() <= 150 && color.getRed() == 0 && color.getGreen() == 0){
						float pixelBlue = mapData.getSampleFloat(x, y, 2);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelBlue/150.)), Players.get("Player"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
					//red [50, 150] => a base for the IA_1
					else if(color.getRed() >= 50  && color.getRed() <= 150 && color.getBlue() == 0 && color.getGreen() == 0){
						float pixelRed = mapData.getSampleFloat(x, y, 0);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelRed/150.)), Players.get("IA_1"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
					//green [50, 150] => a base for the IA_2
					else if(color.getGreen() >= 50  && color.getGreen() <= 150 && color.getBlue() == 0 && color.getRed() == 0){
						float pixelRed = mapData.getSampleFloat(x, y, 0);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelRed/150.)), Players.get("IA_2"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
					//white [50, 150] => a neutral base
					else if(color.getRed() >= 50  && color.getRed() <= 150 && color.getBlue() >= 50  && color.getBlue() <= 150 && color.getGreen() >= 50  && color.getGreen() <= 150){
						float pixelRed = mapData.getSampleFloat(x, y, 0);
						Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixelRed/150.)), Players.get("Neutral"));
						newBase.setId(Renderer.addBaseSprite(newBase));
						Engine.addBase(newBase);
					}
				}
			}
		}
		//For each pixels, create the towers
		for(int y=0;y<map.getHeight();++y){
			for(int x=0;x<map.getWidth();++x){
				Color color = new Color(map.getRGB(x, y));
				//if the pixel is not black
				if(color.getRed() > 0 || color.getBlue() > 0 || color.getGreen() > 0){
					//blue [200, 207] => a tower for the player
					if(color.getBlue() == 200 && color.getRed() == 200 && color.getGreen() == 200){
						//float pixelBlue = mapData.getSampleFloat(x, y, 2);
						// if for the type of Tower
						Tower newTower = new Tower(MAP_SCALE*x, MAP_SCALE*y);
						newTower.setId(Renderer.addTowerSprite(newTower));
						Engine.addTower(newTower);
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
		Players.put("Player", new Player("You", TypeOfPlayer.PLAYER));
		Players.put("IA_1", new Player("Jean Vilain", TypeOfPlayer.IA_1));
		Players.put("IA_2", new Player("Mr Smith", TypeOfPlayer.IA_2));
		Players.put("Neutral", new Player("Neutral", TypeOfPlayer.NEUTRAL));
		
		//init the renderer
		try {
			Dispatcher.Renderer.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//load the map
		try {
			Dispatcher.loadMap("./tex/datamap/datamap_tower.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//display the renderer
		Dispatcher.Renderer.render();
		
		//lauch thread for each player
		Players.get("Player").start();
		Players.get("IA_1").start();
		Players.get("IA_2").start();
		Players.get("Neutral").setDaemon(true);
		Players.get("Neutral").start();
		
		//start the game
		ArrayList<Integer> idDeleted = new ArrayList<Integer>();
		boolean endOfGame = false;
		//=>what we have to do in each frame
		while(!endOfGame) {
			long begin = System.currentTimeMillis();
			
			Dispatcher.nbFrame = Dispatcher.nbFrame + 1;
			
			//work of the engine
			idDeleted = Dispatcher.Engine.doATurnGame();
			//work of the renderer
			Dispatcher.Renderer.refreshView(idDeleted);
			
			//work of the dispatcher : manage interactions between players and the engine
			//create units
			if(BaseSprite.isAStartingBase() && BaseSprite.isAnEndingBase()) {
				try {
					Dispatcher.Renderer.displayRadialMenuMovment(MouseInfo.getPointerInfo().getLocation());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double nbAgentsOfUnitSent = BaseSprite.getStartingBase().getNbAgents() / 2; // agents of the unit sent = 50% of agents in the base
				BaseSprite.getStartingBase().sendUnit(nbAgentsOfUnitSent, BaseSprite.getEndingBase());
				BaseSprite.resetEndingBase();
			}
			else if(BaseSprite.isAStartingBase() && TowerSprite.isAnEndingTower()) {
				double nbAgentsOfUnitSent = BaseSprite.getStartingBase().getNbAgents() / 2; // agents of the unit sent = 50% of agents in the base
				BaseSprite.getStartingBase().sendUnit(nbAgentsOfUnitSent, TowerSprite.getEndingTower());
				TowerSprite.resetEndingTower();
			}
			//check if there is a winner
			if(Players.get("Player").isAlive() && !Players.get("IA_1").isAlive() && !Players.get("IA_2").isAlive()){
				System.out.println("The winner is "+Players.get("Player").getNameOfPlayer());
				try {
					Dispatcher.Renderer.displayWinner();
					Dispatcher.Renderer.render();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(5000);
					endOfGame = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Renderer.getFrame().dispose();
				Player.flagThread = false;
				
			}
			else if((Players.get("IA_1").isAlive() && !Players.get("Player").isAlive() && !Players.get("IA_2").isAlive())
					|| (Players.get("IA_2").isAlive() && !Players.get("Player").isAlive() && !Players.get("IA_1").isAlive())){
				System.out.println("You loose !");
				Renderer.getFrame().dispose();
				Player.flagThread = false;
				endOfGame = true;
			}
			
			//if the loop is too fast, we need to wait 
			long end = System.currentTimeMillis();
			if ((end - begin) < Dispatcher.MILLISECOND_PER_FRAME) {
				try {
					Thread.sleep(Dispatcher.MILLISECOND_PER_FRAME - (end - begin));
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
