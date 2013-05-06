package playable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public enum Player {
	PLAYER("You", TypeOfPlayer.PLAYER),
	IA_1("IA_1", TypeOfPlayer.IA),
	IA_2("IA_2", TypeOfPlayer.IA),
	NEUTRAL("Neutral", TypeOfPlayer.NEUTRAL);
	
	private final String name;
	private final TypeOfPlayer type;
	private HashMap<String, BufferedImage> images;
	
	Player(String name, TypeOfPlayer type){
		this.name = name;
		this.type = type;
		this.images = new HashMap<String, BufferedImage>(2);
	}
	
	public void init(String... pathOfImages) throws IOException{
		this.images.put("base", ImageIO.read(new File(pathOfImages[0])));
		if(pathOfImages.length > 1)
			this.images.put("unit", ImageIO.read(new File(pathOfImages[1])));
	}
	
	// GETTERS & SETTERS

	public String getName() {
		return name;
	}

	public HashMap<String, BufferedImage> getImages() {
		return images;
	}
	
	public BufferedImage getImageOfBase() {
		return images.get("base");
	}
	
	public BufferedImage getImageOfUnit() {
		return images.get("unit");
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
