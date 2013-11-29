package dispatcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TypeOfTower {
	NONE("", "", UnitModifier.NONE),
	DAMAGE("./tex/tower/subtower/subtower_damage.png", "./tex/tower/projectile/proj_damage.png", UnitModifier.NONE),
	POISON("./tex/tower/subtower/subtower_poison.png", "./tex/tower/projectile/proj_poison.png", UnitModifier.NONE),
	FREEZE("./tex/tower/subtower/subtower_freeze.png", "./tex/tower/projectile/proj_freeze.png", UnitModifier.NONE),
	ZONE("./tex/tower/subtower/subtower_zone.png", "./tex/tower/projectile/proj_zone.png", UnitModifier.NONE),
	DIVISION("./tex/tower/subtower/subtower_division.png", "", UnitModifier.DIVISION),
	PROLIFERATION("./tex/tower/subtower/subtower_proliferation.png", "", UnitModifier.PROLIFERATION),
	RESISTANT("./tex/tower/subtower/subtower_resistant.png", "", UnitModifier.RESISTANT),
	SPEED("./tex/tower/subtower/subtower_speed.png", "", UnitModifier.SPEED);
	
	private BufferedImage subSpriteImg;
	private BufferedImage projectileImg;
	private UnitModifier modifier;
	
	TypeOfTower(String subSpritePath, String projPath, UnitModifier modifier){
		try {
			this.subSpriteImg = null;
			this.projectileImg = null;
			
			if(subSpritePath != ""){
				this.subSpriteImg = ImageIO.read(new File(subSpritePath));
			}
			
			if(projPath != ""){
				this.projectileImg = ImageIO.read(new File(projPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.modifier = modifier;
	}
	
	public BufferedImage getSubSprite(){
		return this.subSpriteImg;
	}
	
	public BufferedImage getProjectileSprite(){
		return this.projectileImg;
	}
	
	public UnitModifier getUnitModifier(){
		return this.modifier;
	}
}
