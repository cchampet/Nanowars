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
public class SeletedSprite extends Sprite {
	/**
	 * startingBases and endingBase are static variable, useful to decide in which direction the player sends units.
	 */
	protected static ArrayList<Element> startingElements = new ArrayList<Element>();
	protected static Element endingElement;
	
	public static void resetStartingElements() {
		SeletedSprite.startingElements.clear();
	}
	
	public static void resetEndingElement() {
		SeletedSprite.endingElement = null;
	}
	
	// GETTERS & SETTERS
	
	public static ArrayList<Element> getStartingElements() {
		return startingElements;
	}

	public static void setStartingElements(ArrayList<Element> startingElements) {
		SeletedSprite.startingElements = startingElements;
	}

	public static Element getEndingElement() {
		return endingElement;
	}

	public static void setEndingElement(Element endingElement) {
		SeletedSprite.endingElement = endingElement;
	}

	public static boolean isThereAtLeastOneStartingElement() {
		return BaseSprite.startingElements.isEmpty() ? false : true;
	}
	
	public static boolean isThereAnEndingElement() {
		return BaseSprite.endingElement == null ? false : true;
	}
}
