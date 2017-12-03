package dictionary.controller;

import java.util.ArrayList;

import org.hibernate.Session;

import dictionary.model.Definition;
import dictionary.model.DictionaryEntry;
import dictionary.model.WordSense;
import dictionary.utils.DatabaseUtil;
import dictionary.model.ICollide;
import dictionary.model.PartOfSpeech;
import dictionary.model.WordForm;

/**
 * @author Alex Killian
 *
 * Our controller for our dictionary application.
 */
public class Dictionary implements ICollide<String> {

	private static Dictionary dictionary = null;
	
	/**
	 * Returns the singleton instance of the dictionary controller.
	 * 
	 * @return Dictionary
	 */
	public static Dictionary getInstance() {
		if (dictionary == null)
			dictionary = new Dictionary();
		return dictionary;
	}
	
	/**
	 * Stores the entries (in memory).
	 */
	private ArrayList<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	// Private so it can't be manually created
	private Dictionary() {
	}
	
	/**
	 * Adds a new entry to the dictionary. 
	 * This method will update the model if need be.
	 * 
	 * @param e
	 */
	public void addEntry(DictionaryEntry e) {
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// Make sure each word sense and wordform are saved
		for (WordSense ws : e.getWordSenses()) {
			for (WordForm wf : ws.getWorldForms()) {
				session.saveOrUpdate(wf);
			}
			session.saveOrUpdate(ws);
		}
		if (e.getWordRoot() != null) 
			session.saveOrUpdate(e.getWordRoot());
		
		// Save the entry
		session.saveOrUpdate(e);
		session.getTransaction().commit();
		session.close();
		
		// Add to cache
		if (!entries.contains(e)) entries.add(e);
	}
	
	/**
	 * Adds a new sense to an existing entry.
	 * 
	 * @param ws
	 * @param e
	 */
	public void addSense(WordSense ws, DictionaryEntry e) {
		
	}
	
	public boolean collides(String e) {
		return false;
	}
	
	/**
	 * Removes an entry from this dictionary.
	 * 
	 * @param e
	 */
	public void remove(DictionaryEntry e) {
		
	}
	
	/**
	 * Adds from a given file.
	 */
	public void addFromFile(String file) {
		
	}
	
	/**
	 * Ignores a collision.
	 * 
	 * @param e
	 */
	public void ignore(DictionaryEntry e) {
		
	}
	
	/**
	 * 
	 * @param ws
	 * @param e
	 */
	public void merge(WordSense ws, DictionaryEntry e) {
		
	}
	
	public void merge(WordSense ws, DictionaryEntry e, WordSense ews) {
		
	}
}
