package dictionary.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Alex Killian
 *
 * This class represents a dictionary entry.
 */
@Entity
public class DictionaryEntry implements ICollide<DictionaryEntry> {

	/**
	 * The id that the DB will know this object by.
	 */
	@Id
	@GeneratedValue
	private int id;
	
	/**
	 * The stem for this word.
	 */
	private String wordStem;
	
	/**
	 * The root.
	 */
	private WordForm wordRoot;
	
	/**
	 * All word senses.
	 */
	private ArrayList<WordSense> wordSenses;
	
	public boolean collides(DictionaryEntry entry) {
		return false;
	}
	
	/**
	 * Returns the word senses of this entry.
	 * 
	 * @return
	 */
	public ArrayList<WordSense> getWordSenses() {
		return this.wordSenses;
	}
	
	/**
	 * Resolves a collision between this and the given entry.
	 * 
	 * @param e
	 * @return
	 */
	public List<String> resolveCollision(DictionaryEntry e) {
		return null;
	}
	
	/**
	 * 
	 * @param ws
	 * @return
	 */
	public List<String> merge(WordSense ws) {
		return null;
	}
	
	/**
	 * 
	 * @param ws
	 */
	public void mergeSense(WordSense ws) {

	}
	
	/**
	 * Adds a new word sense to this entry.
	 * 
	 * @param ws
	 */
	public void addSense(WordSense ws) {
		this.wordSenses.add(ws);
	}
}
