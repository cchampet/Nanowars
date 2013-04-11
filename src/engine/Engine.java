package engine;

import java.io.IOException;

import renderer.Renderer;

public class Engine {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		//Create the Renderer
		Renderer renderer = new Renderer("Nano WAAAARS!!!");
		try {
			renderer.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		renderer.render();
	}
}
