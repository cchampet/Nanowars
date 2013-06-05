package renderer;

import java.awt.Container;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JLabel;

import playable.TypeOfPlayer;

// This is a vain attempt to store the elements necessary to trigger an option in the option defined in FinalOption.java.

@SuppressWarnings("serial")
public class EndingMessage extends JLabel {
	
	private Container container;
	private int width;
	private int height;
		
	private LinkedList<FinalOptions> finalOptions;


	public EndingMessage(Container c, int width, int height){
		super();
		
		this.container = c;
		this.height = height;
		this.width = width;
		
		this.finalOptions = new LinkedList<FinalOptions>();
	}
	
	public void init() throws IOException{
		
		// The image is already displayed by UIRenderer's displayWinner(); or displayLoser();
		
		// 2 possible choices
		this.addFinalOptions("./tex/MENU.png", "Back", 270, this.height - 100);
		this.addFinalOptions("./tex/datamap/datamap_tower2.png", "Next", 470, this.height - 100);
	}
	
	/**
	 * Implementing the possible options
	 */
	public int addFinalOptions(String pathOfTheOption, String nameOfTheOption, int x, int y){
		FinalOptions newSprite = new FinalOptions(pathOfTheOption, nameOfTheOption);
		newSprite.setSize(50);
		newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(x, y, 50, 50);
		container.add(newSprite, Layer.UI.id());
		finalOptions.add(newSprite);
		return newSprite.getId();
	}
	
	// GETTERS

	public LinkedList<FinalOptions> getFinalOptions(){
		return finalOptions;
	}
	
}
