package renderer;

import java.io.IOException;

import javax.swing.JFrame;

import engine.Base;

/**
 * Renderer is the main class for all displaying stuff. It creates all specific renderers like MapRenderer.
 * It also manage the main frame.
 * 
 * @author Jijidici
 *
 */

public class Renderer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private JFrame frame;
	private MapRenderer mapRenderer;
		
	/**
	 * Constructor asking for the window title.
	 * @param frameTitle - String containing the title of the game.
	 */
	public Renderer(String frameTitle){
		super();
		this.frame = new JFrame(frameTitle);
		this.mapRenderer = new MapRenderer(this.frame.getLayeredPane(), WIDTH, HEIGHT);
	}
	
	/**
	 * Initialize the Renderer by building every Swing resource and setting the main window.
	 * @throws IOException
	 */
	public void init() throws IOException{
		//Set up the frame
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setResizable(false);
		this.frame.setLayout(null);
		
		//Set up the background image
		this.mapRenderer.init();
	}
	
	/**
	 * Display the main frame.
	 */
	public void render(){
		this.frame.setVisible(true);
	}
	
	/**
	 * Add a Base to the rendering process. That give the chosen base to the MapRenderer.
	 * @param b base to render
	 */
	public void renderABase(Base b){
		this.mapRenderer.renderABase(b);
	}
}
