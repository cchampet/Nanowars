package playable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This class represents the players in our game (including the neutral).
 * @author Yuki
 *
 */
public class Player {
	private String name;
	private TypeOfPlayer type;
	private int nbAgents;

	private HashMap<String, BufferedImage> images;
	
	public Player(String name, TypeOfPlayer type){
		super();
		
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
	
	/**
	 * @return the nbAgents
	 */
	public int getNbAgents() {
		return nbAgents;
	}

	/**
	 * @param nbAgents the nbAgents to set
	 */
	public void setNbAgents(int nbAgents) {
		this.nbAgents = nbAgents;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the images
	 */
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
}
