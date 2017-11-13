package dictionary.model;

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
public class DictionaryEntry {

	/**
	 * The id that the DB will know this object by.
	 */
	@Id
	@GeneratedValue
	private int id;
	
	/**
	 * The stem for this entry. The stem is used for collision detection.
	 */
	private String wordStem;
	
	/**
	 * The root word for this entry. The root word is the main word form for this entry.
	 */
	private String wordRoot; // TODO: replace with the WordForm type...
	
	/**
	 * The word senses for this entry.
	 */
	//private List wordSenses; // TODO: have be a list of WordSense once that type is created
}
