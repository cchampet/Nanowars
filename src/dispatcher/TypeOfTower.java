package dispatcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TypeOfTower {
	DAMAGE("./tex/tower/subtower/subtower_damage.png", UnitModifier.NONE),
	POISON("./tex/tower/subtower/subtower_poison.png", UnitModifier.NONE),
	FREEZE("./tex/tower/subtower/subtower_freeze.png", UnitModifier.NONE),
	ZONE("./tex/tower/subtower/subtower_zone.png", UnitModifier.NONE),
	DIVISION("./tex/tower/subtower/subtower_division.png", UnitModifier.DIVISION),
	PROLIFERATION("./tex/tower/subtower/subtower_proliferation.png", UnitModifier.PROLIFERATION),
	RESISTANT("./tex/tower/subtower/subtower_resistant.png", UnitModifier.RESISTANT),
	SPEED("./tex/tower/subtower/subtower_speed.png", UnitModifier.SPEED);
	
	private BufferedImage subSpriteImg;
	private UnitModifier modifier;
	
	TypeOfTower(String path, UnitModifier modifier){
		try {
			this.subSpriteImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.modifier = modifier;
	}
	
	public BufferedImage getSubSprite(){
		return this.subSpriteImg;
	}
	
	public UnitModifier getUnitModifier(){
		return this.modifier;
	}
}
