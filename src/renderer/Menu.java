package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import playable.TypeOfPlayer;

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
			throw new IOException();
		}
		this.setBounds(0, 0, this.width, this.height);
		this.setIcon(bgMenuImage);	
		
		//currently 3 levels
		this.addLvlSprite("./tex/datamap/datamap_1.png", "1", 270, this.height - 100);
		this.addLvlSprite("./tex/datamap/datamap_2.png", "2", 370, this.height - 100);
		this.addLvlSprite("./tex/datamap/datamap_3.png", "3", 470, this.height - 100);
	}
	
	/**
	 * Add a level to the menu.
	 * @param pathOfTheLevel : path of the corresponding image of the level.
	 * @param nameOfTheLevel : the name of the level, display in the menu.
	 * @param x : position 
	 */
	public int addLvlSprite(String pathOfTheLevel, String nameOfTheLevel, int x, int y){
		LvlSprite newSprite = new LvlSprite(pathOfTheLevel, nameOfTheLevel);
		newSprite.setSize(50);
		newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(x, y, 50, 50);
		container.add(newSprite, Layer.UI.id());
		lvlSprites.add(newSprite);
		return newSprite.getId();
	}
	
	// GETTERS

	public LinkedList<LvlSprite> getLvlSprites(){
		return lvlSprites;
	}
	
	public boolean isGameNotBegun() {
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.isSelected())
				return false;
		}
		return true;
	}
	
	public String getPathOfTheLevelSelected(){
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.isSelected())
				return lvl.getPathOfTheLevel();
		}
		return null;
	}
}
