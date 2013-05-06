package playable;

/**
 * This interface will be implemented by each player of the game.
 * @author Yuki
 *
 */
public interface Playable {
	/**
	 * Return if the player lost or not (stop the thread)
	 */
	public boolean lost();
	/**
	 * This procedure is used by the IA.
	 */
	public void chooseAction();
}
