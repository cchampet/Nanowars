package playable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This enum contains the representation of the different type of player in the game (the player, the IA, and neutral).
 * @author Yuki
 *
 */
public enum TypeOfPlayer {
	PLAYER("./tex/player/player.png", "./tex/base/basePlayer.png", "./tex/tower/towerPlayer_lvl0.png", "./tex/tower/towerPlayer_lvlup.png", "./tex/unit/unitPlayer.png"), 
	IA_1("./tex/player/ia_1.png", "./tex/base/baseIA_1.png", "./tex/tower/towerIA_1_lvl0.png", "./tex/tower/towerIA_1_lvlup.png", "./tex/unit/unitIA_1.png"),
	IA_2("./tex/player/ia_2.png", "./tex/base/baseIA_2.png", "./tex/tower/towerIA_2_lvl0.png", "./tex/tower/towerIA_2_lvlup.png", "./tex/unit/unitIA_2.png"),
	NEUTRAL("./tex/player/deadPlayer.png", "./tex/base/baseNeutral.png", "./tex/tower/towerNeutral_lvl0.png", "./tex/tower/towerNeutral_lvlup.png");
	
	private HashMap<String, BufferedImage> images;
		
	TypeOfPlayer(String... pathOfImages){
		this.images = new HashMap<String, BufferedImage>(pathOfImages.length);
		
		try {
			this.images.put("player", ImageIO.read(new File(pathOfImages[0])));
			this.images.put("base", ImageIO.read(new File(pathOfImages[1])));
			this.images.put("tower_lvl0", ImageIO.read(new File(pathOfImages[2])));
			this.images.put("tower_lvlup", ImageIO.read(new File(pathOfImages[3])));
			
			if(pathOfImages.length > 4) {
				this.images.put("unit", ImageIO.read(new File(pathOfImages[4])));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// GETTERS & SETTERS
	
	public HashMap<String, BufferedImage> getImages() {
		return images;
	}
	
	public BufferedImage getImageOfBase() {
		return images.get("base");
	}
	
	public BufferedImage getImageOfUnit() {
		return images.get("unit");
	}
	
	public BufferedImage getImageOfTowerLvl0() {
		return images.get("tower_lvl0");
	}
	
	public BufferedImage getImageOfTowerLvlup() {
		return images.get("tower_lvlup");
	}
	
	public BufferedImage getImageOfPlayer() {
		return images.get("player");
	}
}
