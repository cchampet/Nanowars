package dispatcher;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import renderer.Sprite;

public enum TypeOfTower {
	DAMAGE("./tex/tower/subtower/subtower_damage.png"),
	POISON("./tex/tower/subtower/subtower_damage.png"),
	FREEZE("./tex/tower/subtower/subtower_damage.png"),
	ZONE("./tex/tower/subtower/subtower_damage.png"),
	DIVISION("./tex/tower/subtower/subtower_damage.png"),
	PROLIFERATION("./tex/tower/subtower/subtower_damage.png"),
	RESISTANT("./tex/tower/subtower/subtower_damage.png"),
	SPEED("./tex/tower/subtower/subtower_damage.png");
	
	private Sprite subSprite;
	
	TypeOfTower(String path){
		try {
			this.subSprite = new Sprite();
			this.subSprite.setImage(ImageIO.read(new File(path)));
			this.subSprite.setSize(160);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Sprite getSubSprite(){
		return this.subSprite;
	}
}
