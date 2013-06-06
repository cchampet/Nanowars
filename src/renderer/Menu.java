package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import playable.TypeOfPlayer;
import renderer.sprite.LvlSprite;

@SuppressWarnings("serial")
public class Menu extends JLabel {
	
	private Container container;
	private int width;
	private int height;
		
	private LinkedList<LvlSprite> lvlSprites;

	public Menu(Container c, int width, int height){
		super();
		
		this.container = c;
		this.height = height;
		this.width = width;
				
		this.lvlSprites = new LinkedList<LvlSprite>();
	}
	
	public void init() throws IOException{
		//Load the menu background image
		ImageIcon bgMenuImage = new ImageIcon("./tex/MENU.png");
		if(bgMenuImage.getImageLoadStatus() != MediaTracker.COMPLETE){
		}
		this.setBounds(0, 0, this.width, this.height);
		this.setIcon(bgMenuImage);	
	}
	
	/**
	 * Add a level to the menu.
	 * @param pathOfTheLevel : path of the corresponding image of the level.
	 * @param numOfTheLevel : the number of the level, display in the menu.
	 * @param x : x position 
	 * @param y : y position
	 */
	public int addLvlSprite(String pathOfTheLevel, int numOfTheLevel, int x, int y){
		LvlSprite newSprite = new LvlSprite(pathOfTheLevel, numOfTheLevel);
		newSprite.setSize(50);
		newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(x, y, 50, 50);
		container.add(newSprite, Layer.UI.id());
		lvlSprites.add(newSprite);
		return newSprite.getId();
	}
	
	public void resetLvlSelected(){
		this.getLvlSelected().resetIsSelected();
	}
	
	public void addLvlsToTheMenu(){
		//currently 3 levels
		this.addLvlSprite(Level.LVL_1.getPath(), Level.LVL_1.getId(), 270, this.height - 100);
		this.addLvlSprite(Level.LVL_2.getPath(), Level.LVL_2.getId(), 370, this.height - 100);
		this.addLvlSprite(Level.LVL_3.getPath(), Level.LVL_3.getId(), 470, this.height - 100);
	}
	
	// GETTERS

	public LinkedList<LvlSprite> getLvlSprites(){
		return lvlSprites;
	}
	
	public LvlSprite getLvlSprite(int id){
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.getId() == id)
				return lvl;
		}
		return null;
	}
	
	/**
	 * This function returns the level selected in the menu, or null if the user doesn't make a choice yet.
	 * @return
	 */
	public LvlSprite getLvlSelected() {
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.isSelected())
				return lvl;
		}
		return null;
	}
}
