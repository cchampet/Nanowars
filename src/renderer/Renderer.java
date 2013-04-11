package renderer;

import java.io.IOException;

import javax.swing.JFrame;

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private JFrame f;
	private MapRenderer mapRend;
	
	public Renderer(String frameTitle){
		super();
		this.f = new JFrame(frameTitle);
		this.mapRend = new MapRenderer(this.f.getContentPane(), WIDTH, HEIGHT);
	}
	
	public void init() throws IOException{
		//Set up the frame
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.setSize(WIDTH, HEIGHT);
		this.f.setResizable(false);
		this.f.setLayout(null);
		
		//Set up the background image
		this.mapRend.init();
	}
	
	public void render(){
		this.f.setVisible(true);
	}
}
