package dispatcher;

import java.io.IOException;

import renderer.Renderer;
import engine.Engine;

/**
 * This class represents the link between the engine, the renderer, and the IHM of the player.
 * @author Yuki
 *
 */
public class Dispatcher {
	private static final Engine Engine = new Engine();
	private static final Renderer Renderer = new Renderer("Nano WAAAARS!!!");
	
	public Engine getEngine() {
		return Dispatcher.Engine;
	}
	
	public Renderer getRenderer() {
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
			Dispatcher.Engine.loadMap("./tex/datamap.png", Dispatcher.Renderer);
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
