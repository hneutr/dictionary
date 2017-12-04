import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.model.Definition;
import dictionary.model.DictionaryEntry;
import dictionary.model.PartOfSpeech;
import dictionary.model.WordForm;
import dictionary.model.WordSense;
import dictionary.utils.DatabaseUtil;

public class DictionaryTest {

	private static final WordForm WF = new WordForm("test");
	private static final Definition DEF = new Definition("test");
	private static final PartOfSpeech POS = new PartOfSpeech("Noun");
	private static final WordSense WS = new WordSense(DEF, POS);
	
	private static DictionaryEntry getTestEntry() {
		
		// test word form
		WordForm wf = WF;
		
		// test word sense
		WordSense ws = WS;
		ws.addWordForm(wf);
		
		// test entry
		DictionaryEntry e = new DictionaryEntry("word", wf);
		e.addSense(ws);
		return e;
	}
	
	private static void checkTestEntry(DictionaryEntry e) {
		
		// Retrieve entry from DB
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		DictionaryEntry re = (DictionaryEntry) session.get(DictionaryEntry.class, e.getId());
		session.getTransaction().commit();
		session.close();
		
		// Check id
		assertEquals(e.getId(), re.getId());
		
		// Check root word
		assertEquals(e.getWordRoot().getWordForm(), WF.getWordForm());
		
		// Check word sense
		for (WordSense ws : e.getWordSenses()) {
			for (WordForm wf : ws.getWorldForms()) {
				assertEquals(wf.getWordForm(), WF.getWordForm());
			}
		}
	}

	@Test
	public void addRemoveEntryTest() {
		
		/*
		 * Case 0: add entry.
		 */
		
		// Get a test entry and try to add it
		DictionaryEntry e = getTestEntry();
		Dictionary.getInstance().addEntry(e);
		
		// Make sure it was added correctly
		checkTestEntry(e);
		
		/*
		 * Case 1: remove entry
		 */
		Dictionary.getInstance().remove(e);
	}

}
