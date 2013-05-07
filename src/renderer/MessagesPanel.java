package renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MessagesPanel extends JPanel {
	BufferedImage image;
	 
    public MessagesPanel(BufferedImage image) {
        this.image = image;
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw image centered.
        int x = (getWidth() - image.getWidth())/2;
        int y = (getHeight() - image.getHeight())/2;
        g.drawImage(image, x, y, this);
    }

}
