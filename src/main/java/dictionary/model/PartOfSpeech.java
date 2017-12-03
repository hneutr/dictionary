package dictionary.model;

import javax.persistence.Embeddable;

/**
 * @author Adam Wiemerslage
 *
 * This class represents a part of speech in a dictionary entry.
 */
@Embeddable
public class PartOfSpeech implements ICollide<PartOfSpeech> {
	
	/**
	 * The Part of Speech label
	 */
	private String partOfSpeech;
	
	public PartOfSpeech() {
	}
	
	public PartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	
	public boolean collides(PartOfSpeech pos) {
		return pos.getPartOfSpeech().equals(getPartOfSpeech());
	}
}