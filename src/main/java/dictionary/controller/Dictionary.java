package dictionary.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import dictionary.model.DictionaryEntry;
import dictionary.model.FileUtils;
import dictionary.model.WordSense;
import dictionary.utils.DatabaseUtil;
import dictionary.model.ICollide;
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
	private List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	// Private so it can't be manually created
	private Dictionary() {
		this.entries = this.getAllEntries();
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
	 * This will also make sure both the given entry and word sense are in the DB
	 * and add them if they are not, or update if they are.
	 * 
	 * @param ws
	 * @param e
	 */
	public void addSense(WordSense ws, DictionaryEntry e) {
		
		// Guard against null.
		if (e == null) {
			System.err.println("Can't add sense to null entry!");
			return;
		}
		if (ws == null) {
			System.err.println("Can't add null sense to entry!");
			return;
		}
		
		// Add the word sense
		e.addSense(ws);
		
		// Make sure all is saved
		addEntry(e);
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
		
		// Remove from cache
		int i = entries.indexOf(e);
		if (i >= 0) entries.remove(i);
		
		// Remove from DB
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// Delete all word senses and word entries
		for (WordSense ws : e.getWordSenses()) {
			for (WordForm wf : ws.getWorldForms()) {
				session.delete(wf);
			}
			session.delete(ws);
		}
		if (e.getWordRoot() != null) 
			session.delete(e.getWordRoot());
		
		// Delete entry
		session.delete(e);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Adds from a given file.
	 */
	public void addFromFile(String file) {
		FileUtils utils = new FileUtils();
		Collection<DictionaryEntry> es = utils.getEntries(file);
		for (DictionaryEntry e : es) {
			addEntry(e);
		}
	}
	
	/**
	 * Look up in the dictionary.
	 */
	public DictionaryEntry lookupByEntry(String str) {
		
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Collection<DictionaryEntry> es = getAllEntries();
		for (DictionaryEntry e : es) {
			if (e.getWordRoot().getWordForm().equals(str)) {
				session.getTransaction().commit();
				session.close();
				return e;
			}
		}
		session.getTransaction().commit();
		session.close();
		return null;
	}
	
	/**
	 * Returns all entries.
	 * 
	 * @return
	 */
	public List<DictionaryEntry> getAllEntries() {
		//if (this.entries == null) {
			Session session = DatabaseUtil.getSessionFactory().openSession();
			session.beginTransaction();
			List<DictionaryEntry> es = session.createQuery("from DictionaryEntry").list();
			session.getTransaction().commit();
			session.close();
			return es;
		//}
		//return this.entries;
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
