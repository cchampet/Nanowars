package renderer;


import java.awt.Container;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import playable.Player;
import playable.TypeOfPlayer;
import renderer.sprite.LvlSprite;
import renderer.sprite.MultipleSprite;
import renderer.sprite.PlayerSprite;
import renderer.sprite.SelectedSprite;
import renderer.sprite.Sprite;
import renderer.sprite.TowerSprite;
import dispatcher.Dispatcher;
import dispatcher.TypeOfTower;
import engine.Base;
import engine.Element;

public class UIRenderer {
	
	private JLabel winnerBackground;
	private JLabel loserBackground;
	
	private JLabel radialMenuMovment;
	private MultipleSprite radialMenuTower;
	
	private Container container;
	
	private Menu menu;
	
	private int width;
	private int height;
	
	/**
	 * playerSprites contains all sprites which display the info of players (total nbAgents...)
	 */
	private final ArrayList<PlayerSprite> playerSprites;
	
	/**
	 * Represent the state of UIRenderer during unit to send choice. 
	 * 0 = not choosing, 
	 * 1 = choosing, 
	 * 2 = already chosen
	 */
	private static int choosingUnitFlag = 0;
	private static double unitPercentChosen = 0.5;
	/**
	 * Represent the state of UIRenderer during tower choice. 
	 * 0 = not choosing, 
	 * 1 = chose tower super type, 
	 * 2 = chose offensive tower, 
	 * 3 = chose defensive tower,
	 * 4 = already chosen
	 */
	private static int choosingTowerFlag = 0;
	
	public UIRenderer(Container c, int width, int height){
		super();
		
		this.winnerBackground = new JLabel();
		this.loserBackground = new JLabel();
		
		this.radialMenuMovment = new JLabel();
		this.radialMenuTower = new MultipleSprite(3);
		
		this.container = c;
		
		this.height=height;
		this.width=width;
		
		this.playerSprites = new ArrayList<PlayerSprite>();
		
		this.menu = new Menu(c, width, height);
	}
	
	public void init() throws IOException{
		
		// Initialize menu
		this.menu.init();
		
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
				
		
		//Load the radial menu image for unit choice
		ImageIcon rmImage = new ImageIcon("./tex/radialmenu_movment.png");
		if(rmImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		this.radialMenuMovment.setIcon(rmImage);
		this.radialMenuMovment.setSize(rmImage.getIconWidth(), rmImage.getIconHeight());
		
		//Load the radial menu image for tower choice
		this.radialMenuTower.setSize(80);
		this.radialMenuTower.setImage(ImageIO.read(new File("./tex/radialmenu_tower.png")));
		this.radialMenuTower.setSize(this.radialMenuTower.getSpriteSize(), this.radialMenuTower.getSpriteSize());
		
		//Manage events
		this.radialMenuMovment.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//fix the bug of controling opponent units when he takes our base during the choice
				boolean aSeletedBaseIsNotAPlayerBase = false;
				for(Element element:SelectedSprite.getStartingElements()){
					if(element.getClass() == Base.class){
						if(!((Base) element).isAPlayerBase())
							aSeletedBaseIsNotAPlayerBase = true;
					}
				}
				if(aSeletedBaseIsNotAPlayerBase){
					SelectedSprite.resetStartingElements();
					SelectedSprite.resetEndingElement();
					TowerSprite.resetTowerToBuild();
					Dispatcher.getRenderer().hideRadialMenus();
					return;
				}
				
				UIRenderer.choosingUnitFlag = 2;
				JLabel radialMenu = (JLabel) arg0.getComponent();
				Point rmCenter = new Point(radialMenu.getWidth()/2, radialMenu.getHeight()/2);
				Point mousePosition = arg0.getPoint();
				
				if(rmCenter.distance(mousePosition) < 10){ //click on the center 50%
					UIRenderer.unitPercentChosen = 0.5;
				}else{
					if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on top quarter 50%
						UIRenderer.unitPercentChosen = 0.5;
					}else if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on right quarter 75%
						UIRenderer.unitPercentChosen = 0.75;						
					}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on left quarter 25%
						UIRenderer.unitPercentChosen = 0.25;
					}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on bottom quarter 99%
						UIRenderer.unitPercentChosen = 1.;						
					}
				}
			}
		});
		
		this.radialMenuTower.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MultipleSprite radialMenu = (MultipleSprite) arg0.getComponent();
				Point rmCenter = new Point(radialMenu.getWidth()/2, radialMenu.getHeight()/2);
				Point mousePosition = arg0.getPoint();
				//click out of the center circle
				if(rmCenter.distance(mousePosition) > 10){
					if(UIRenderer.choosingTowerFlag == 1){
						//choose offensive or defensive
						if(mousePosition.x - rmCenter.x < 0){ //left side offensive
							UIRenderer.choosingTowerFlag = 2;
						}else{ //right side defensive
							UIRenderer.choosingTowerFlag = 3;
						}
					}else{
						if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on top quarter
							if(UIRenderer.choosingTowerFlag == 2){ //offensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.SPEED);
							}else if(UIRenderer.choosingTowerFlag == 3){ //defensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.FREEZE);
							}
						}else if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on right quarter
							if(UIRenderer.choosingTowerFlag == 2){ //offensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.DIVISION);
							}else if(UIRenderer.choosingTowerFlag == 3){ //defensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.ZONE);
							}						
						}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on left quarter
							if(UIRenderer.choosingTowerFlag == 2){ //offensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.RESISTANT);
							}else if(UIRenderer.choosingTowerFlag == 3){ //defensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.DAMAGE);
							}
						}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on bottom quarter
							if(UIRenderer.choosingTowerFlag == 2){ //offensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.PROLIFERATION);
							}else if(UIRenderer.choosingTowerFlag == 3){ //defensive wheel
								TowerSprite.setChosenTowerType(TypeOfTower.POISON);
							}					
						}
						UIRenderer.choosingTowerFlag = 4;
					}
				}
			}
		});
	}
	
	/**
	 * Display a "WINNER" message when the player wins
	 */
	public void displayWinner(){
		this.container.add(this.winnerBackground, Layer.UI.id());
	}
	
	/**
	 * Display a "LOSER" message when the player loses
	 */
	public void displayLoser(){
		this.container.add(this.loserBackground, Layer.UI.id());
	}
	
	/**
	 * Display the menu at the beginning of the game
	 */	
	public void displayMenu(){
		this.container.add(this.menu, Layer.UI.id());
	}
	
	/**
	 * Display or hide a radial menu to choose how many units to send 
	 */
	public void refreshRadialMenuMovment(){
		switch(UIRenderer.choosingUnitFlag){
			//if the player haven't chosen yet
			case 0:
				if(SelectedSprite.isThereAtLeastOneStartingElement() && SelectedSprite.isThereAnEndingElement()){
					UIRenderer.choosingUnitFlag = 1;
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePosition, this.container);
					mousePosition.x -= this.radialMenuMovment.getWidth()/2;
					mousePosition.y -= this.radialMenuMovment.getHeight()/2;
					this.radialMenuMovment.setLocation(mousePosition);
				}
				break;
			
			//if the player is choosing
			case 1:
				if(this.radialMenuMovment.getParent() == null){
					this.container.add(this.radialMenuMovment, Layer.UI.id());
				}
				break;
			
			//if the player has just chosen
			case 2:
				this.container.remove(this.radialMenuMovment);
				UIRenderer.choosingUnitFlag = 0;
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * Display or hide the radial menu to choose tower
	 */
	public void refreshRadialMenuTower(){
		switch(UIRenderer.choosingTowerFlag){
			//if the player haven't chosen yet
			case 0:
				if(TowerSprite.isThereOneTowerToBuild()){
					this.radialMenuTower.goToSprite(0);
					UIRenderer.choosingTowerFlag = 1;
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePosition, this.container);
					mousePosition.x -= this.radialMenuTower.getWidth()/2;
					mousePosition.y -= this.radialMenuTower.getHeight()/2;
					this.radialMenuTower.setLocation(mousePosition);
				}
				break;
				
			//if the player is choosing tower type
			case 1:
				if(this.radialMenuTower.getParent() == null){
					this.container.add(this.radialMenuTower, Layer.UI.id());
				}
				break;
				
			//if the player is choosing offensive tower
			case 2:
				this.radialMenuTower.goToSprite(1);
				break;
				
			//if the player is choosing defensive tower
			case 3:
				this.radialMenuTower.goToSprite(2);
				break;
			
			//if the player has just chosen
			case 4:
				if(!TowerSprite.isThereOneTowerToBuild()){
					this.container.remove(this.radialMenuTower);
					this.radialMenuTower.goToSprite(0);
					UIRenderer.choosingTowerFlag = 0;
				}
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * Hide the menu
	 */
	public void hideMenu(){
		for(Sprite s:this.menu.getLvlSprites())
			this.container.remove(s);
		this.container.remove(this.menu);
	}
	
	/**
	 * Hide the radial menu for unit choice and re-initialize it
	 */
	public void hideRadialMenuMovment(){
		UIRenderer.choosingUnitFlag = 0;
		if(this.radialMenuMovment.getParent() != null){
			this.container.remove(this.radialMenuMovment);
		}
	}
	
	/**
	 * Hide the radial menu for tower choice and re-initialize it
	 */
	public void hideRadialMenuTower(){
		UIRenderer.choosingTowerFlag = 0;
		if(this.radialMenuTower.getParent() != null){
			this.container.remove(this.radialMenuTower);
		}
	}
	
	/**
	 * Add the player's images at the bottom of the window.
	 * @param newPlayers
	 */
	public void addPlayerSprites(HashMap<String, Player> newPlayers){		
		PlayerSprite newSpritePlayer = new PlayerSprite(newPlayers.get("Player"));
		newSpritePlayer.setImage(TypeOfPlayer.PLAYER.getImageOfPlayer());
		newSpritePlayer.setBounds(10, this.height - newSpritePlayer.getSpriteSize() - 30, newSpritePlayer.getSpriteSize(), newSpritePlayer.getSpriteSize());
		this.container.add(newSpritePlayer, new Integer(Layer.UI.id()));
		this.playerSprites.add(newSpritePlayer);
		
		PlayerSprite newSpriteIA_1 = new PlayerSprite(newPlayers.get("IA_1"));
		newSpriteIA_1.setImage(TypeOfPlayer.IA_1.getImageOfPlayer());
		newSpriteIA_1.setBounds(this.width - 10 - newSpriteIA_1.getSpriteSize(), this.height - newSpriteIA_1.getSpriteSize() - 30, newSpriteIA_1.getSpriteSize(), newSpriteIA_1.getSpriteSize());
		this.container.add(newSpriteIA_1, new Integer(Layer.UI.id()));
		this.playerSprites.add(newSpriteIA_1);
		
		if(newPlayers.size() == 3){
			PlayerSprite newSpriteIA_2 = new PlayerSprite(newPlayers.get("IA_2"));
			newSpriteIA_2.setImage(TypeOfPlayer.IA_2.getImageOfPlayer());
			newSpriteIA_2.setBounds(this.width / 2, this.height - newSpriteIA_2.getSpriteSize() - 30, newSpriteIA_2.getSpriteSize(), newSpriteIA_2.getSpriteSize());
			this.container.add(newSpriteIA_2, new Integer(Layer.UI.id()));
			this.playerSprites.add(newSpriteIA_2);
		}
	}
	
	// GETTERS & SETTERS
	
	public Menu getMenu(){
		return menu;
	}
	
	public boolean isChoosingUnitFlag(){
		if(UIRenderer.choosingUnitFlag == 1){
			return true;
		}
		return false;
	}
	
	public double getUnitPercentChosen(){
		return UIRenderer.unitPercentChosen;
	}
	
	/**
	 * Check if the player have chosen his tower type
	 * @return boolean - true if the tower type is chosen
	 */
	public boolean isTowerTypeChosen(){
		if(UIRenderer.choosingTowerFlag == 4){
			return true;
		}
		return false;
	}

	public ArrayList<PlayerSprite> getPlayerSprites() {
		return playerSprites;
	}
	
	public LvlSprite getLvlSelected() {
		return this.menu.getLvlSelected();
	}
}



