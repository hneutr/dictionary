package dictionary.controller;

import java.util.ArrayList;
import dictionary.model.DictionaryEntry;
import dictionary.model.WordSense;
import dictionary.model.ICollide;

/**
 * @author Alex Killian
 *
 * Our controller for our dictionary application.
 */
public class Dictionary implements ICollide<String> {

	/**
	 * Stores the entries (in memory).
	 */
	private ArrayList<DictionaryEntry> entries;
	
	/**
	 * Adds a new entry to the dictionary.
	 * 
	 * @param e
	 */
	public void addEntry(DictionaryEntry e) {
		
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
