package renderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class display a base.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class BaseSprite extends Sprite{

	public BaseSprite() {
		super();
		
		this.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(":MOUSE_RELEASED_EVENT:");
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(":MOUSE_PRESSED_EVENT:");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //System.out.println(":MOUSE_EXITED_EVENT:");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println(":MOUSE_ENTER_EVENT:");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(":MOUSE_CLICK_EVENT:");
            }
        });
	}

}
