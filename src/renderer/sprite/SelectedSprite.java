package renderer.sprite;

import java.util.ArrayList;

import engine.Element;

/**
 * This class display an element (a base, a tower, a unit...).
 * Useful to select several elements in the map.
 * @author Yuki
 *
 */
@SuppressWarnings("serial")
public abstract class SelectedSprite extends Sprite {
	/**
	 * startingBases and endingBase are static variable, useful to decide in which direction the player sends units.
	 */
	protected static ArrayList<Element> startingElements = new ArrayList<Element>();
	protected static Element endingElement;
	
	public static void resetStartingElements() {
		SelectedSprite.startingElements.clear();
	}
	
	public static void resetEndingElement() {
		SelectedSprite.endingElement = null;
	}
	
	// GETTERS & SETTERS
	
	public static ArrayList<Element> getStartingElements() {
		return startingElements;
	}

	public static void setStartingElements(ArrayList<Element> startingElements) {
		SelectedSprite.startingElements = startingElements;
	}

	public static Element getEndingElement() {
		return endingElement;
	}

	public static void setEndingElement(Element endingElement) {
		SelectedSprite.endingElement = endingElement;
	}

	public static boolean isThereAtLeastOneStartingElement() {
		return SelectedSprite.startingElements.isEmpty() ? false : true;
	}
	
	public static boolean isThereAnEndingElement() {
		return SelectedSprite.endingElement == null ? false : true;
	}
}
