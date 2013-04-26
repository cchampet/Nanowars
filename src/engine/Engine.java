package engine;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import renderer.Renderer;

/**
 * 
 * Engine Class is the physic engine class which run the program.
 * Contain the main function and create the renderer and every useful elements.
 * 
 * @author Jijidici
 *
 */

public class Engine extends Thread {
	private static final int FRAME_RATE =  60;
	private static final int MILLISECOND_PER_FRAME =  1000 / FRAME_RATE;
	private static long nbFrame = 0;
	
	private static final int MAP_SCALE =  5;
	
	/**
	 * Data of out game
	 */
	private static final ArrayList<Base> bases = new ArrayList<Base>();
	
	/**
	 * This method load the map from a datamap image.
	 * 
	 * @param filepath path of the datamap grey-scale image
	 * @param r render, used to render the base after they are created
	 * @throws IOException
	 */
	public void loadMap(String filepath, Renderer r) throws IOException{
		BufferedImage map = ImageIO.read(new File(filepath));
		Raster mapData = map.getData();
		
		//For each pixels
		for(int y=0;y<map.getHeight();++y){
			for(int x=0;x<map.getWidth();++x){
				//if the pixel is not black, add a base
				float pixel = mapData.getSampleFloat(x, y, 0);
				if(pixel > 50.f){
					Base newBase = new Base(MAP_SCALE*x, MAP_SCALE*y, (int)(Base.MAX_CAPACITY*(pixel/255.)));
					r.renderABase(newBase);
					bases.add(newBase);
				}
			}
		}
	}
	
	/**
	 * This method is the loop which enable us to compute our stuff (bases, units...) regulary.
	 * @throws InterruptedException 
	 */
	public void startGame(){
		// loop of our application
		while(true) {
			long begin = System.currentTimeMillis();

			// what we have to do in each frame
			Engine.nbFrame++;
			
			long end = System.currentTimeMillis();
			// wait if it's too fast, we need to wait 
			if ((end - begin) < Engine.MILLISECOND_PER_FRAME) {
				try {
					Engine.sleep(Engine.MILLISECOND_PER_FRAME - (end - begin));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
