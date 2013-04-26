package dispatcher;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import renderer.Renderer;
import renderer.Sprite;
import engine.Base;
import engine.Engine;

/**
 * This class represents the link between the engine, the renderer, and the IHM of the player.
 * @author Yuki
 *
 */
public class Dispatcher {
	private static final int MAP_SCALE =  5;
	
	private static final Engine Engine = new Engine();
	private static final Renderer Renderer = new Renderer("Nano WAAAARS!!!");
	
	private static final LinkedList<Sprite> sprites = new LinkedList<Sprite>();
	
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
					Renderer.renderABase(newBase);
					Engine.addBase(newBase);
				}
			}
		}
	}
	
	public final Engine getEngine() {
		return Dispatcher.Engine;
	}
	
	public final Renderer getRenderer() {
		return Dispatcher.Renderer;
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
		Dispatcher.Engine.startGame();
	}

}
