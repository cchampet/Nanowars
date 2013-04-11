package renderer;

import javax.swing.JFrame;

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private JFrame f;
	
	public Renderer(String frameTitle){
		super();
		this.f = new JFrame(frameTitle);
	}
	
	public void init(){
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.setSize(WIDTH, HEIGHT);
		this.f.setResizable(false);
		
		this.f.setVisible(true);
	}
}
