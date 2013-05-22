package playable;

import dispatcher.Dispatcher;
import engine.Base;
import engine.tower.Tower;

/**
 * This class represents a player in the game. It's a thread, which is running while the player has at least one base or one unit.
 * @author Yuki
 *
 */
public class Player extends Thread implements Playable {
	private final String name;
	/**
	 * The type of the player is an enum, which contains all images we need to display the right elements (bases, towers...)
	 */
	private final TypeOfPlayer type;
	/**
	 * flagThread is useful for stop the last player's thread (at the end of the game).
	 */
	public static boolean flagThread = true; 
	public int buildTowerCounter = 0;
	public Player(String name, TypeOfPlayer type){
		super();
		
		this.name = name;
		this.type = type;
	}
	
	public void run() {
		while(!this.lost() && flagThread){
			//if(this.isIA())
				//chooseAction();
		}
	}
	
	@Override
	public void chooseAction() {
		for(Base baseOfHim:Dispatcher.getEngine().getBasesOfAPlayer(this)){
			if(baseOfHim.getNbAgents() > 0.8*baseOfHim.getCapacity()){
				// The IA builds Towers once every 3 turns if it isn't in minority
				if(buildTowerCounter == 3 
						&& Dispatcher.getEngine().getBasesOfAPlayer(baseOfHim.getOwner()).size()
						>= Dispatcher.getEngine().getAdversaryBasesOfAPlayer(baseOfHim.getOwner()).size()
				){
					buildTowerCounter=0;
					for(Tower towerAround:Dispatcher.getEngine().getTowerAround(baseOfHim)){
						baseOfHim.sendUnit(baseOfHim.getNbAgents()/5,towerAround);
					}
				}
				else{
					Base goal=null;
					double param=1000000000;
					for(Base potentialGoal:Dispatcher.getEngine().getAdversaryBasesOfAPlayer(this)){
						if(!potentialGoal.equals(baseOfHim)){
							if(10*potentialGoal.getNbAgents()+potentialGoal.distanceToElement(baseOfHim)<param){
								goal=potentialGoal;
								param=10*potentialGoal.getNbAgents()+potentialGoal.distanceToElement(baseOfHim);
							}
						}
					}
					baseOfHim.sendUnit(baseOfHim.getNbAgents() / 3, goal);
					if(buildTowerCounter < 3){
						buildTowerCounter++;
					}
				}
			}	
		}
	}
	
	@Override
	public boolean lost(){
		return (Dispatcher.getEngine().getBasesOfAPlayer(this).isEmpty() && Dispatcher.getEngine().getUnitsOfAPlayer(this).isEmpty()) ? true : false;
	}
	
	// GETTERS & SETTERS

	public String getNameOfPlayer() {
		return name;
	}

	public TypeOfPlayer getType() {
		return type;
	}
	
	public boolean isNeutral(){
		return this.type == TypeOfPlayer.NEUTRAL ? true : false;
	}

	public boolean isIA(){
		return (this.type == TypeOfPlayer.IA_1 || this.type == TypeOfPlayer.IA_2) ? true : false;
	}

	public boolean isPlayer(){
		return this.type == TypeOfPlayer.PLAYER ? true : false;
	}
}
