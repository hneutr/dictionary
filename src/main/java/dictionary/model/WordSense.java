package dictionary.model;

import java.util.ArrayList;

/**
 * @author alexkillian
 * 
 * The class for word senses. A dictionary can have one or more 
 * WordSense objects and a WordSense can only belong to one DictionaryEntry.
 */
public class WordSense implements ICollide {

	/**
	 * Word forms of this word sense.
	 */
	private ArrayList<WordForm> wordForms;
	
	/**
	 * The definition for this word sense.
	 */
	private Definition definition;
	
	/**
	 * The POS for this word sense.
	 */
	private PartOfSpeech partOfSpeech;
	
	/**
	 * Returns all word forms associated with this WordSense.
	 * 
	 * @return
	 */
	public ArrayList<WordForm> getWorldForms() {
		
	}
	
	@Override
	public boolean collides(Object o) {
		return false;
	}
	
	/**
	 * Sets the definition for this word sense.
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	/**
	 * Sets the part of the speech.
	 */
	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	/**
	 * Merges this word sense with the given one.
	 * 
	 * @param wordSense
	 * @return
	 */
	public String merge(WordSense wordSense) {
		
	}
	
	/**
	 * Adds a word form to this word sense.
	 * 
	 * @param wordFrom
	 */
	public void addWordForm(WordForm wordFrom) {
		wordForms.add(wordForm);
	}
}
