package renderer;

import java.util.ArrayList;

import engine.Element;

/**
 * This class display an element (a base, a tower, a unit...).
 * Useful to select several elements in the map.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public class ElementSprite extends Sprite {
	/**
	 * startingBases and endingBase are static variable, useful to decide in which direction the player sends units.
	 */
	protected static ArrayList<Element> startingElements = new ArrayList<Element>();
	protected static Element endingElement;
	
	public static void resetStartingElements() {
		ElementSprite.startingElements.clear();
	}
	
	public static void resetEndingElement() {
		ElementSprite.endingElement = null;
	}
	
	// GETTERS & SETTERS
	
	public static ArrayList<Element> getStartingElements() {
		return startingElements;
	}

	public static void setStartingElements(ArrayList<Element> startingElements) {
		ElementSprite.startingElements = startingElements;
	}

	public static Element getEndingElement() {
		return endingElement;
	}

	public static void setEndingElement(Element endingElement) {
		ElementSprite.endingElement = endingElement;
	}

	public static boolean isThereAtLeastOneStartingElement() {
		return BaseSprite.startingElements.isEmpty() ? false : true;
	}
	
	public static boolean isThereAnEndingElement() {
		return BaseSprite.endingElement == null ? false : true;
	}
}
