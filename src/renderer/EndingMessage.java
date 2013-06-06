package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import playable.TypeOfPlayer;
import renderer.sprite.LvlSprite;

// This is a vain attempt to store the elements necessary to trigger an option in the option defined in FinalOption.java.

@SuppressWarnings("serial")
public class EndingMessage extends JLabel {
	
	private Container container;
	private int width;
	private int height;

	private JLabel loserBackground;
	private JLabel winnerBackground;
	
	private LvlSprite previousLvl;
	private LvlSprite currentLvl;
	private LvlSprite nextLvl;

	public EndingMessage(Container c, int width, int height){
		super();
		
		this.container = c;
		this.width = width;
		this.height = height;
		
		this.winnerBackground = new JLabel();
		this.loserBackground = new JLabel();
	}
	
	public void init() throws IOException{
		//Load the winner background image
		ImageIcon bgWinnerImage = new ImageIcon("./tex/youWin.png");
		if(bgWinnerImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}		
		this.winnerBackground.setBounds(0, 0, this.width, this.height);
		this.winnerBackground.setIcon(bgWinnerImage);
		
		//Load the loser background image
		ImageIcon bgLoserImage = new ImageIcon("./tex/youLose.png");
		if(bgLoserImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		this.loserBackground.setBounds(0, 0, this.width, this.height);
		this.loserBackground.setIcon(bgLoserImage);
	}
	
	/**
	 * Add a previous level button at the end of a game (you win or you loose).
	 * @param previousLevel : the lvlSprite of the previous level.
	 */
	public int addLvlSpritePrevious(LvlSprite previousLvl){
		previousLvl.changeTheNameOfTheLvlDisplay("<");
		previousLvl.setSize(50);
		previousLvl.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		previousLvl.setBounds(130, 280, 50, 50);
		container.add(previousLvl, Layer.UI.id());
		this.previousLvl = previousLvl;
		return previousLvl.getId();
	}
	
	/**
	 * Add a current level button at the end of a game (you win or you loose).
	 * @param currentLevel : the lvlSprite of the current level.
	 */
	public int addLvlSpriteCurrent(LvlSprite currentLvl){
		currentLvl.changeTheNameOfTheLvlDisplay("retry");
		currentLvl.setSize(50);
		currentLvl.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		currentLvl.setBounds(400, 430, 50, 50);
		container.add(currentLvl, Layer.UI.id());
		this.currentLvl = currentLvl;
		return currentLvl.getId();
	}
	
	/**
	 * Add a next level button at the end of a game (you win or you loose).
	 * @param nextLevel : the lvlSprite of the next level.
	 */
	public int addLvlSpriteNext(LvlSprite nextLevel){
		nextLevel.changeTheNameOfTheLvlDisplay(">");
		nextLevel.setSize(50);
		nextLevel.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		nextLevel.setBounds(620, 280, 50, 50);
		container.add(nextLevel, Layer.UI.id());
		this.nextLvl = nextLevel;
		return nextLevel.getId();
	}
	
	// GETTERS

	public JLabel getLoserBackground() {
		return loserBackground;
	}

	public JLabel getWinnerBackground() {
		return winnerBackground;
	}
	
	public LvlSprite getPreviousLvl() {
		return previousLvl;
	}

	public LvlSprite getNextLvl() {
		return nextLvl;
	}
	
	public LvlSprite getCurrentLvl(){
		return currentLvl;
	}
}
