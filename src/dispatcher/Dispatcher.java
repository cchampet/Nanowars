package dispatcher;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	
	/**
	 * This method load the map from a datamap image.
	 * 
	 * @param filepath path of the datamap grey-scale image
	 * @param r render, used to render the base after they are created
	 * @throws IOException
	 */
	public static void loadMap(String filepath) throws IOException{
		BufferedImage map = ImageIO.read(new File(filepath));
		Raster mapData = map.getData();
		
		//For each pixels
		for(int y=0;y<map.getHeight();++y){
			for(int x=0;x<map.getWidth();++x){
				//if the pixel is not black, add a base
				float pixel = mapData.getSampleFloat(x, y, 0);
				if(pixel > 50.f){
					Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixel/255.)));
					newBase.setId(Renderer.addBaseSprite(newBase));
					Engine.addBase(newBase);
				}
			}
		}
	}
	
	/**
	 * @param args input arguments
	 * @throws IOException 
	 */
	public static void main(String[] args){	
		//init the renderer
		try {
			Dispatcher.Renderer.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//load the map
		try {
			Dispatcher.loadMap("./tex/datamap.png");
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
			// create units if it's necessary
			if(BaseSprite.isAStartingPoint() && BaseSprite.isAnEndingPoint()) {
				double nbAgentsOfUnitSent = BaseSprite.getStartingBase().getNbAgents() / 2; // agents of the unit = 50% of agents in the base
				Point2D.Float startingPoint = new Point2D.Float(BaseSprite.getStartingPoint().x + ((float)nbAgentsOfUnitSent / 2), BaseSprite.getStartingPoint().y + ((float)nbAgentsOfUnitSent / 2));
						
				Unit newUnit = new Unit(nbAgentsOfUnitSent, startingPoint, BaseSprite.getEndingPoint());
				newUnit.setId(Renderer.addUnitSprite(newUnit));
				Engine.addUnit(newUnit);
				
				BaseSprite.getStartingBase().reduceNbAgents(nbAgentsOfUnitSent);
				
				BaseSprite.resetEndingPoint();
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
	
	public Engine getEngine() {
		return Dispatcher.Engine;
	}
	
	public Renderer getRenderer() {
		return Dispatcher.Renderer;
	}
}
