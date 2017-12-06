package dictionary.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.Session;

import dictionary.utils.DatabaseUtil;

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
		this.wordStem = this.wordRoot.getStem();
	}
	
	/**
	 * The root.
	 */
	@OneToOne(fetch=FetchType.EAGER)
	private WordForm wordRoot;
	
	/**
	 * All word senses.
	 */
	@OneToMany(fetch=FetchType.EAGER)  
	private Collection<WordSense> wordSenses = new ArrayList<WordSense>();
	
	public boolean collides(DictionaryEntry entry) {
		String comparisonEntryWordRoot = entry.getWordRoot().getWordForm();
		
		if (this.wordStem.equals(comparisonEntryWordRoot))
			return true;
		else if (this.wordRoot.equals(comparisonEntryWordRoot))
			return true;
		else {
			for (WordSense curWordSense : this.getWordSenses()) {
				for (WordSense comparisonWordSense : entry.getWordSenses()) {
					if (curWordSense.collides(comparisonWordSense))
						return true;
				}
			}
		}
		
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
	 * Return the word sense that corresponds to the given index
	 * 
	 * @param wordSenseIdx: The index of the word sense
	 * @return The actual WordSense at that index
	 */
	public WordSense getWordSenseByIdx(int wordSenseIdx) {
		int i = 0;
		for (WordSense sense : this.getWordSenses()) {
			if (wordSenseIdx == i) {
				return sense;
			}
			i++;
		}
		return null;
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
	
	public void removeSense(WordSense ws) {
		this.wordSenses.remove(ws);
	}
	
	public WordForm getWordRoot() {
		return this.wordRoot;
	}
	
	public void setWordRoot(WordForm wf) {
		this.wordRoot = wf;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		String output = "";
		output = output + "WordRoot: " + this.wordRoot.getWordForm() + "\n";
		
		int counter = 1;
		for (WordSense sense : this.getWordSenses()) {
			output = output + "Sense " + counter + ":\n" + sense.toString();
			counter++;
		}
		
		return output;
	}
}
