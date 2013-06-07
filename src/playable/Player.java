package playable;

import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import engine.Base;
import engine.tower.Tower;
import engine.Unit;

/**
 * This class represents a player in the game. It's a thread, which is running
 * while the player has at least one base or one unit.
 * 
 * @author Yuki
 * 
 */
public class Player extends Thread implements Playable {
	private final String name;
	/**
	 * The type of the player is an enum, which contains all images we need to
	 * display the right elements (bases, towers...)
	 */
	private final TypeOfPlayer type;
	/**
	 * flagThread is useful for stop the last player's thread (at the end of the
	 * game).
	 */
	public static boolean flagThread = true;
	public int sendUnitsToTowerCounter = 0;
	public int checkTowerConstructionCounter = 0;

	public Player(String name, TypeOfPlayer type) {
		super();

		this.name = name;
		this.type = type;
	}

	public void run() {
		while (!this.lost() && flagThread) {
			if (this.isIA())
				chooseAction();
		}
	}

	@Override
	public void chooseAction() {
		for (Base baseOfHim : Dispatcher.getEngine().getBasesOfAPlayer(this)) {
			if (baseOfHim.getNbAgents() > 0.7 * baseOfHim.getCapacity()) {
				// The IA builds Towers once every 5 turns max
				if (sendUnitsToTowerCounter > 2) {
					sendUnitsToTowerCounter = 0;
					for (Tower tower : Dispatcher.getEngine()
							.getTowersOfAPlayer(this)) {
						if (!tower.isLevelMax()
								&& !tower.isWaitingForBuilding())
							baseOfHim.sendUnit(baseOfHim.getNbAgents() / 5,
									tower);
					}
				} else {
					Base goal = null;
					double param = 1000000000;
					for (Base potentialGoal : Dispatcher.getEngine()
							.getAdversaryBasesOfAPlayer(this)) {
						if (!potentialGoal.equals(baseOfHim)) {
							if (10
									* potentialGoal.getNbAgents()
									+ potentialGoal
											.distanceToElement(baseOfHim) < param) {
								goal = potentialGoal;
								param = 10
										* potentialGoal.getNbAgents()
										+ potentialGoal
												.distanceToElement(baseOfHim);
							}
						}
					}
					baseOfHim.sendUnit(baseOfHim.getNbAgents() / 4, goal);
					sendUnitsToTowerCounter++;
				}
			}
		}

		// Tower construction
		if (this.checkTowerConstructionCounter >= 10) {
			this.checkTowerConstructionCounter = 0;

			// browse player's tower
			for (Tower tower : Dispatcher.getEngine().getTowersOfAPlayer(this)) {
				if (tower.isWaitingForBuilding()) {
					int randomTowerNumber = (int) (Math.random() * 7.);
					TypeOfTower chosenType = null;
					switch (randomTowerNumber) {
					case 0:
						chosenType = TypeOfTower.RESISTANT;
						break;
					case 1:
						chosenType = TypeOfTower.POISON;
						break;
					case 2:
						chosenType = TypeOfTower.PROLIFERATION;
						break;
					case 3:
						chosenType = TypeOfTower.DAMAGE;
						break;
					case 4:
						chosenType = TypeOfTower.SPEED;
						break;
					case 5:
						chosenType = TypeOfTower.FREEZE;
						break;
					case 6:
						chosenType = TypeOfTower.DIVISION;
						break;
					case 7:
						chosenType = TypeOfTower.ZONE;
						break;
					default:
						chosenType = TypeOfTower.NONE;
						break;
					}
					Tower specTower = Dispatcher.getEngine().specializeTower(chosenType, tower);
					Dispatcher.getRenderer().updateTowerSprite(specTower, tower.getId(), chosenType);
				}
			}
		} else {
			this.checkTowerConstructionCounter++;
		}
	}

	@Override
	public boolean lost() {
		return (Dispatcher.getEngine().getBasesOfAPlayer(this).isEmpty() && Dispatcher
				.getEngine().getUnitsOfAPlayer(this).isEmpty()) ? true : false;
	}

	public static void resetFlagThread() {
		flagThread = true;
	}

	// GETTERS & SETTERS

	public String getNameOfPlayer() {
		return name;
	}

	public TypeOfPlayer getType() {
		return type;
	}

	public boolean isIA() {
		return (this.type == TypeOfPlayer.IA_1 || this.type == TypeOfPlayer.IA_2) ? true
				: false;
	}

	public boolean isPlayer() {
		return this.type == TypeOfPlayer.PLAYER ? true : false;
	}

	public int getTotalNbAgents() {
		int totalNbAgents = 0;
		for (Base b : Dispatcher.getEngine().getBasesOfAPlayer(this))
			totalNbAgents += b.getNbAgents();
		for (Unit u : Dispatcher.getEngine().getUnitsOfAPlayer(this))
			totalNbAgents += u.getNbAgents();
		return totalNbAgents;
	}
}
