package engine;

import renderer.Renderer;

public class Engine {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Create the Renderer
		Renderer renderer = new Renderer("Nano WAAAARS!!!");
		renderer.init();
	}

}
