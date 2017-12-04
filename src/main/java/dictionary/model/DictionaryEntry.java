package dictionary.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	
	private DictionaryEntry() {
	}
	
	/**
	 * Constructor for DictionaryEntry.
	 * 
	 * @param wordStem
	 */
	public DictionaryEntry(WordForm wordRoot) {
		this.wordRoot = wordRoot;
		this.wordStem = wordRoot.getStem();
	}
	
	/**
	 * The root.
	 */
	@OneToOne
	private WordForm wordRoot;
	
	/**
	 * All word senses.
	 */
	@OneToMany
	private Collection<WordSense> wordSenses = new ArrayList<WordSense>();
	
	public boolean collides(DictionaryEntry entry) {
		return false;
	}
	
	/**
	 * Returns the word senses of this entry.
	 * 
	 * @return
	 */
	public Collection<WordSense> getWordSenses() {
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
	
	public WordForm getWordRoot() {
		return this.wordRoot;
	}
	
	public int getId() {
		return id;
	}
}
