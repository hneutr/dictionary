package dictionary.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import dictionary.model.Definition;
import dictionary.model.DictionaryEntry;
import dictionary.model.FileUtils;
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
	private List<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	// Private so it can't be manually created
	private Dictionary() {
		this.entries = this.getAllEntries();
	}
	
	/**
	 * Creates and adds a new entry.
	 * 
	 * @param word
	 */
	public void addDictionaryEntry(String word) {
		DictionaryEntry e = new DictionaryEntry(new WordForm(word));
		addEntry(e);
	}
	
	/**
	 * Adds a new blank word sense to the matching entry(ies)
	 */
	public void addWordSense(String word) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			e.addSense(new WordSense(new Definition(""), new PartOfSpeech("")));
			addEntry(e); // make sure entry is up to date
		}
	}
	
	/**
	 * Adds a def to an specified word sense
	 */
	public void addDefinition(String word, int idx, String def) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) {
				ws.setDefinition(new Definition(def));
				addEntry(e); // make sure entry is up to date
			}
		}
	}
	
	/**
	 * Adds a POS to an specified word sense
	 */
	public void addPartOfSpeech(String word, int idx, String pos) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) {
				ws.setPartOfSpeech(new PartOfSpeech(pos));
				addEntry(e); // make sure entry is up to date
			}
		}
	}
	
	/**
	 * Adds a word form to an specified word sense
	 */
	public void addWordForm(String word, int idx, String form) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) {
				ws.addWordForm(new WordForm(form));
				addEntry(e); // make sure entry is up to date
			}
		}
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
	public void removeDictionaryEntry(String word) {
		
		// Lookup
		Collection<DictionaryEntry> es = lookupByEntry(word);	
		for (DictionaryEntry e : es) {
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
	 * Look up by word.
	 */
	public Collection<DictionaryEntry> lookupByEntry(String word) {
		Collection<DictionaryEntry> results = new ArrayList<DictionaryEntry>();
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		for (DictionaryEntry e : getAllEntries()) {
			if (e.getWordRoot().getWordForm().equals(word)) {
				results.add(e);
			}
			else {
				WSFor:
				for (WordSense ws : e.getWordSenses()) {
					for (WordForm wf : ws.getWorldForms()) {
						if (wf.getWordForm().equals(word)) {
							results.add(e);
							break WSFor;
						}
					}
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Lookup by definition.
	 */
	public Collection<DictionaryEntry> lookupByDefinition(String def) {
		Collection<DictionaryEntry> results = new ArrayList<DictionaryEntry>();
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		for (DictionaryEntry e : getAllEntries()) {
			for (WordSense ws : e.getWordSenses()) {
				if (ws.getDefinition().getDefinition().equals(def)) {
					results.add(e);
					break;
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Lookup by POS.
	 */
	public Collection<DictionaryEntry> lookupByPartOfSpeech(String pos) {
		Collection<DictionaryEntry> results = new ArrayList<DictionaryEntry>();
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		for (DictionaryEntry e : getAllEntries()) {
			for (WordSense ws : e.getWordSenses()) {
				if (ws.getPartOfSpeech().getPartOfSpeech().equals(pos)) {
					results.add(e);
					break;
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Update entry spelling
	 */
	public void updateDictionaryEntry(String word, String newWord) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			e.setWordRoot(new WordForm(newWord));
			addEntry(e); // make sure entry is up to date
		}
	}
	
	/**
	 * Update definition
	 */
	public void updateDefinition(String word, int idx, String def) {
		addDefinition(word, idx, def);
	}
	
	/**
	 * Update POS
	 */
	public void updatePartOfSpeech(String word, int idx, String pos) {
		addPartOfSpeech(word, idx, pos);
	}
	
	/**
	 * Update word form
	 */
	public void updateWordForm(String word, int idx, String oldWordForm, String newWordForm) {
		Collection<DictionaryEntry> es = lookupByEntry(word);
		for (DictionaryEntry e : es) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) {
				for (WordForm wf : ws.getWorldForms()) {
					if (wf.getWordForm().equals(oldWordForm)) {
						wf.setWordForm(newWordForm);
					}
				}
				addEntry(e); // make sure entry is up to date
			}
		}
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
	 * Removes the def from the specified word sense.
	 * 
	 * @param word
	 * @param idx
	 */
	public void removeDefinition(String word, int idx) {
		Collection<DictionaryEntry> entries = lookupByEntry(word);
		for (DictionaryEntry e : entries) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) ws.removeDefinition();
		}
	}
	
	/**
	 * Removes the specified word sense.
	 * 
	 * @param word
	 * @param idx
	 */
	public void removeWordSense(String word, int idx) {
		Collection<DictionaryEntry> entries = lookupByEntry(word);
		for (DictionaryEntry e : entries) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) e.removeSense(ws);
		}
	}
	
	/**
	 * Removes the specified POS.
	 * 
	 * @param word
	 * @param idx
	 */
	public void removePartOfSpeech(String word, int idx) {
		Collection<DictionaryEntry> entries = lookupByEntry(word);
		for (DictionaryEntry e : entries) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) ws.removePartOfSpeech();
		}
	}
	
	/**
	 * Removes the specified word form
	 * 
	 * @param word
	 * @param idx
	 */
	public void removeWordForm(String word, int idx, String wordFormStr) {
		Collection<DictionaryEntry> entries = lookupByEntry(word);
		for (DictionaryEntry e : entries) {
			WordSense ws = e.getWordSenseByIdx(idx);
			if (ws != null) {
				for (WordForm wf : ws.getWorldForms()) {
					if (wf.getWordForm().equals(wordFormStr)) {
						ws.removeWordForm(wf);
					}
				}
			}
		}
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
