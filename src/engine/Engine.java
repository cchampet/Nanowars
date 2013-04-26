package engine;

import java.util.ArrayList;

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
	
	/**
	 * Data of out game
	 */
	private static final ArrayList<Base> bases = new ArrayList<Base>();
	
	public void addBase(Base newBase){
		bases.add(newBase);
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
			Engine.nbFrame = Engine.nbFrame + 1;
			for(Base b:bases){
			    b.prodAgents();
			}

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
	
	public int getBaseID(int index){
		return bases.get(index).getId();
	}
}
