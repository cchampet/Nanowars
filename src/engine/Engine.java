package engine;

import java.io.IOException;

import renderer.Renderer;

public class Engine {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//Create the Renderer
		Renderer renderer = new Renderer("Nano WAAAARS!!!");
		renderer.init();
	}
}
