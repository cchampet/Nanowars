package renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * This class is a generic class, which enable us to display an element based on an image.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class Sprite extends JPanel{
	private int size;
	private BufferedImage image;
	
	public Sprite(){
		super();
		this.size = 100;
		this.image = null;
		this.setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image != null){
			g.drawImage(image, 0, 0, size, size, this);
		}
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public void setImage(BufferedImage img){
		this.image = img;
	}
}
