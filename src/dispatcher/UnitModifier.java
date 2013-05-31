package dispatcher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum UnitModifier {
	NONE("NONE", ""),
	SPEED("SPEED", "./tex/unit/modifiers/mod_speed.png"),
	PROLIFERATION("PROLIFERATION", "./tex/unit/modifiers/mod_proliferation.png"),
	DIVISION("DIVISION", "./tex/unit/modifiers/mod_division.png"),
	RESISTANT("RESISTANT", "./tex/unit/modifiers/mod_resistant.png");
	
	private String name;
	private BufferedImage modIcon;
	
	UnitModifier(String name, String pathIcon){
		this.name= name;
		if(pathIcon == ""){
			this.modIcon = null;
		}else{
			try {
				this.modIcon = ImageIO.read(new File(pathIcon));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public BufferedImage getIcon(){
		return this.modIcon;
	}
}
