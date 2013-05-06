package playable;

import dispatcher.Dispatcher;

/**
 * This class represents a player in the game. It's a thread, which is running while the player has at least one base or one unit.
 * @author Yuki
 *
 */
public class Player extends Thread implements Playable {
	private final String name;
	private final TypeOfPlayer type;
	/**
	 * flagThread is useful for stop the last player's thread (at the end of the game).
	 */
	public static boolean flagThread = true; 
	
	public Player(String name, TypeOfPlayer type){
		super();
		
		this.name = name;
		this.type = type;
	}
	
	public void run() {
		while(!this.lost() && flagThread){
			//System.out.println(this.name+" : already in the course !");
		}
	}

	@Override
	public void chooseAction() {
		
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
		return this.type == TypeOfPlayer.IA ? true : false;
	}

	public boolean isPlayer(){
		return this.type == TypeOfPlayer.PLAYER ? true : false;
	}
}
