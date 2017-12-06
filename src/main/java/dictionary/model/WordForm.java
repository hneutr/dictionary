package dictionary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 
 * @author Adam Wiemerslage
 *
 * This class represents a word form in a dictionary entry.
 */
@Entity
public class WordForm implements ICollide<WordForm>{
	
	/**
	 * The id that the DB will know this object by.
	 */
	@Id
    @GeneratedValue
	private int id;
	
	/**
	 * The text for the word form token
	 */
	String wordForm;
	
	private WordForm() {
		this.stemmerAlgo = new PorterStemmer();
	}
	
	/**
	 * Constructor.
	 */
	public WordForm(String wordForm) {
		this();
		this.wordForm = wordForm;
	};
	
	/**
	 * The algorithm for stemming that IStemmer sets
	 */
	@Transient
	IStemmer stemmerAlgo;
	
	public String getWordForm() {
		return wordForm;
	}
	
	public void setWordForm(String wordForm) {
		this.wordForm = wordForm;
	}
	
	public String getStem() {
		return stemmerAlgo.stem(getWordForm());
	}
	
	public boolean collides(WordForm wf) {
		return wf.getWordForm().equals(getWordForm());
	}
}