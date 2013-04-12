package renderer;

import java.io.IOException;

import javax.swing.JFrame;

import engine.Base;

/**
 * Renderer is the main class for all displaying stuff. It create all specific renderers like MapRenderer.
 * It also manage the main frame.
 * 
 * @author Jijidici
 *
 */

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private JFrame f;
	private MapRenderer mapRend;
		
	/**
	 * Constructor asking for the window title.
	 * @param frameTitle - String containing the title of the game.
	 */
	public Renderer(String frameTitle){
		super();
		this.f = new JFrame(frameTitle);
		this.mapRend = new MapRenderer(this.f.getLayeredPane(), WIDTH, HEIGHT);
	}
	
	/**
	 * Initialize the Renderer by building every Swing resource and setting the main window.
	 * @throws IOException
	 */
	public void init() throws IOException{
		//Set up the frame
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.setSize(WIDTH, HEIGHT);
		this.f.setResizable(false);
		this.f.setLayout(null);
		
		//Set up the background image
		this.mapRend.init();
	}
	
	/**
	 * Display the main frame.
	 */
	public void render(){
		this.f.setVisible(true);
	}
	
	/**
	 * Add a Base to the rendering process. That give the chosen base to the MapRenderer.
	 * @param b base to render
	 */
	public void renderABase(Base b){
		this.mapRend.renderABase(b);
	}
}
