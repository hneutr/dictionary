package dictionary.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author alexkillian
 * 
 * The class for word senses. A dictionary can have one or more 
 * WordSense objects and a WordSense can only belong to one DictionaryEntry.
 */
@Entity
public class WordSense implements ICollide<WordSense> {
	
	/**
	 * The id that the DB will know this object by.
	 */
	@Id
    @GeneratedValue
	private int id;

	/**
	 * Word forms of this word sense.
	 */
	@OneToMany(fetch=FetchType.EAGER)  
	private Collection<WordForm> wordForms = new ArrayList<WordForm>();
	
	/**
	 * The definition for this word sense.
	 */
	@Embedded
	private Definition definition;
	
	/**
	 * The POS for this word sense.
	 */
	@Embedded
	private PartOfSpeech partOfSpeech;
	
	private WordSense() {
	}
	
	/**
	 * WordSense constructor.
	 * 
	 * @param definition
	 * @param partOfSpeech
	 */
	public WordSense(Definition definition, PartOfSpeech partOfSpeech) {
		this.definition = definition;
		this.partOfSpeech = partOfSpeech;
	}
	
	/**
	 * Returns all word forms associated with this WordSense.
	 * 
	 * @return
	 */
	public Collection<WordForm> getWorldForms() {
		return this.wordForms;
	}
	
	public boolean collides(WordSense o) {
		if (this.definition.collides(o.definition))
			return true;
		else if (this.partOfSpeech.collides(o.partOfSpeech))
			return true;
		else {
			for (WordForm curWordForm : this.wordForms ) {
				for (WordForm compWordForm : o.wordForms) {
					if ( curWordForm.collides(compWordForm))
						return true;
				}
			}
		}
	
		return false;
	}
	
	/**
	 * Sets the definition for this word sense.
	 */
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	/**
	 * Returns the definition.
	 * 
	 * @return
	 */
	public Definition getDefinition() {
		return this.definition;
	}
	
	/**
	 * Sets the part of the speech.
	 */
	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	/**
	 * Returns the POS.
	 * @return
	 */
	public PartOfSpeech getPartOfSpeech() {
		return this.partOfSpeech;
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
	
	/**
	 * Removes the definition from this word sense.
	 * 
	 * @return
	 */	
	public void removeDefinition() {
		setDefinition(new Definition(""));
	}

	/**
	 * Removes the PartOfSpeech from this word sense.
	 * 
	 * @return
	 */	
	public void removePartOfSpeech() {
		setPartOfSpeech(new PartOfSpeech(""));
	}

	/**
	 * Removes the specified WordForm from this word sense.
	 * 
	 * @return
	 */	
	public void removeWordForm(WordForm wf) {
		for (Iterator<WordForm> iter = this.getWorldForms().iterator(); iter.hasNext(); ) {
			WordForm ownWordForm = iter.next();

			if (ownWordForm.collides(wf)) {
				iter.remove();
			}
		}
	}
	
	public String toString() {
		String output = "";
		
		String definitionString = (this.definition != null) ? this.definition.getDefinition() : "";
		
		if (! definitionString.isEmpty())
			output = output + "\tDefinition: " + definitionString + "\n";
		
		String partOfSpeechString = (this.partOfSpeech != null) ? this.partOfSpeech.getPartOfSpeech() : "";
		if (! partOfSpeechString.isEmpty())
			output = output + "\tPartOfSpeech: " + partOfSpeechString + "\n";
		
		if (! this.wordForms.isEmpty()){
			output = output + "\tWordForms: \n";
			for (WordForm curWordForm : this.wordForms ) {
				output = output + "\t\t" + curWordForm.getWordForm() + "\n";
			}
		}
				
		return output;
	}
}
