package dictionary.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author alexkillian
 * 
 * The class for word senses. A dictionary can have one or more 
 * WordSense objects and a WordSense can only belong to one DictionaryEntry.
 */
//@Entity
public class WordSense implements ICollide<WordSense> {
	
	/**
	 * The id that the DB will know this object by.
	 */
	//@Id
    //@GeneratedValue
	//private int id;

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
		return this.wordForms;
	}
	
	@Override
	public boolean collides(WordSense o) {
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
		return "";
	}
	
	/**
	 * Adds a word form to this word sense.
	 * 
	 * @param wordFrom
	 */
	public void addWordForm(WordForm wordFrom) {
		wordForms.add(wordFrom);
	}
}
