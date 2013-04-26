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
			Engine.nbFrame++;
			int i=0;
			for(Base b:bases){
				i++;
			    b.prodAgents();
			    System.out.println("Base" + i + " : " + b.getNbAgents() + " agents.");
			}

			Engine.nbFrame = Engine.nbFrame + 1;
			//System.out.println("Number of frames from the beginning : "+Engine.nbFrame);

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
