package dictionary.model;

/**
 * @author Adam Wiemerslage
 *
 * This class represents a part of speech in a dictionary entry.
 */

public class PartOfSpeech implements ICollide {
	
	/**
	 * The Part of Speech label
	 */
	private String partOfSpeech;
	
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	
	public boolean collides(PartOfSpeech pos) {
		return pos.getPartOfSpeech().equals(getPartOfSpeech());
	}
}