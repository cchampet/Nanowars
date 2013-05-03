package engine;

import java.awt.Point;
import java.util.ArrayList;

import javax.vecmath.Vector2f;

import renderer.BaseSprite;
import renderer.Renderer;
import renderer.UnitSprite;

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
	 * Data of our game
	 */
	private static final ArrayList<Base> bases = new ArrayList<Base>();
	private static final ArrayList<Unit> units = new ArrayList<Unit>();
	
	/**
	 * Add a created base to the list of bases, contained by the Engine.
	 * @param newBase 
	 */
	public void addBase(Base newBase){
		bases.add(newBase);
	}
	
	/**
	 * Add a unit to the list of units, contained by the Engine.
	 * @param newBase 
	 */
	public void addUnit(Unit newUnit){
		units.add(newUnit);
	}
	
	/**
	 * This method is the loop which enable us to compute our stuff (bases, units...) regulary.
	 * @param renderer 
	 * @throws InterruptedException 
	 */
	public void startGame(Renderer renderer){
		// loop of our application
	
		
		while(true) {
			long begin = System.currentTimeMillis();

			// what we have to do in each frame
			Engine.nbFrame = Engine.nbFrame + 1;
			
			// production of bases
			for(Base b:bases){
			    b.prodAgents();
			    // update the display of nbAgents
			    BaseSprite correspondingBaseSprite = ((BaseSprite)renderer.getSprite(b.getId()));
			    correspondingBaseSprite.getNbAgents().setText(String.valueOf(b.getNbAgents()));
			}
			
			// move units
			for(Unit unit:units){
				unit.move();
			}
			for(UnitSprite unitSprite:renderer.getUnitSprites()){
				Point newPoint = new Point((int)this.getUnit(unitSprite.getId()).getPosition().x, (int)this.getUnit(unitSprite.getId()).getPosition().y);
				unitSprite.setLocation(newPoint);
			}
			
			// create units if it's necessary
			if(BaseSprite.isAStartingPoint() && BaseSprite.isAnEndingPoint()) {
				double nbAgentsOfUnitSent = BaseSprite.getStartingBase().getNbAgents() / 2; // agents of the unit = 50% of agents in the base 
				Unit newUnit = new Unit(BaseSprite.getStartingBase().getNbAgents()/2, new Vector2f(BaseSprite.getStartingPoint().x + 50, BaseSprite.getStartingPoint().y), BaseSprite.getEndingPoint());
				newUnit.setId(renderer.addUnitSprite(newUnit));
				this.addUnit(newUnit);
				
				BaseSprite.getStartingBase().reduceNbAgents(nbAgentsOfUnitSent);
				
				BaseSprite.resetEndingPoint();
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
	
	/**
	 * Get the id of a base at a specific index in the list of bases.
	 * @param index
	 * @return an id
	 */
	public int getBaseIDAt(int index){
		return bases.get(index).getId();
	}
	
	/**
	 * Get the unit by an id.
	 * @param id
	 * @return unit
	 */
	public Unit getUnit(int id){
		for(Unit unit:units){
			if(unit.getId() == id){
				return unit;
			}
		}
		return null;
	}
}
