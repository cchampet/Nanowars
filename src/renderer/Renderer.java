package renderer;

import java.awt.Container;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private JFrame f;
	private JLabel background;
	
	public Renderer(String frameTitle){
		super();
		this.f = new JFrame(frameTitle);
		this.background = new JLabel("Toto");
	}
	
	public void init() throws IOException{
		//Set up the frame
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.setSize(WIDTH, HEIGHT);
		this.f.setResizable(false);
		this.f.setLayout(null);
		Container c = this.f.getContentPane();
		
		//Set up the background image
		//Load the background image
		ImageIcon bgImage = new ImageIcon("./tex/background.jpg");
		if(bgImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		
		this.background.setBounds(0, 0, WIDTH, HEIGHT);
		this.background.setIcon(bgImage);
		c.add(this.background);
	}
	
	public void render(){
		this.f.setVisible(true);
	}
}
