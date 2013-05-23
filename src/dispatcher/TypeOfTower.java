package dispatcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TypeOfTower {
	DAMAGE("./tex/tower/subtower/subtower_damage.png"),
	POISON("./tex/tower/subtower/subtower_poison.png"),
	FREEZE("./tex/tower/subtower/subtower_freeze.png"),
	ZONE("./tex/tower/subtower/subtower_zone.png"),
	DIVISION("./tex/tower/subtower/subtower_division.png"),
	PROLIFERATION("./tex/tower/subtower/subtower_proliferation.png"),
	RESISTANT("./tex/tower/subtower/subtower_resistant.png"),
	SPEED("./tex/tower/subtower/subtower_speed.png");
	
	private BufferedImage subSpriteImg;
	
	TypeOfTower(String path){
		try {
			this.subSpriteImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public BufferedImage getSubSprite(){
		return this.subSpriteImg;
	}
}
