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
	PLAYER("./tex/basePlayer.png", "./tex/unitPlayer.png"), 
	IA("./tex/baseIA.png", "./tex/unitIA.png"),
	NEUTRAL("./tex/baseNeutral.png");
	
	private HashMap<String, BufferedImage> images;
		
	TypeOfPlayer(String... pathOfImages){
		this.images = new HashMap<String, BufferedImage>(pathOfImages.length);
		
		try {
			this.images.put("base", ImageIO.read(new File(pathOfImages[0])));
			if(pathOfImages.length > 1)
				this.images.put("unit", ImageIO.read(new File(pathOfImages[1])));
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
}
