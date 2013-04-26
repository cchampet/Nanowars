package renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Sprite extends JComponent{
	private int size;
	
	public Sprite(){
		super();
		this.size = 0;
	}
	
	public void loadImage(String filePath) throws IOException{
		BufferedImage img = null;
		img = ImageIO.read(new File(filePath));
		this.update(img.getGraphics());
	}
	
	@Override
	protected void paintComponent(Graphics g){
		this.repaint(0, 0, this.size, this.size);
	}
	
	public void setSize(int size){
		this.size = size;
	}
}
