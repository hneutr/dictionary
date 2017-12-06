import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.model.Definition;
import dictionary.model.DictionaryEntry;
import dictionary.model.PartOfSpeech;
import dictionary.model.WordForm;
import dictionary.model.WordSense;
import dictionary.utils.DatabaseUtil;

public class DictionaryTest {

	// Defs for loaded test entries
	private static final String RUN_DEF_STR = "move at a speed faster than a walk and never have both or all the feet on the ground at the same time.";
	private static final String SMILE_DEF_STR = "form one's features into a pleased or kind or amused expression";
	private static final String DANCE_DEF_STR = "move rhythmically to music";
	private static final String CHEESE_DEF_STR = "a food made from the pressed curds of milk";
	private static final String GARGLE_DEF_STR ="wash one's mouth and throat with a liquid kept in motion by exhaling through it.";
	
	// Properties for test entry
	private static final String WF_STR = "walk";
	private static final String DEF_STR = "move via ones feet";
	private static final String POS_STR = "Verb";
	
	private static <T> T getFirst(Collection<T> list) {
		for (T item : list) return item;
		return null;
	}
	
	private static DictionaryEntry getTestEntry() {
		
		// test word form
		WordForm wf = new WordForm(WF_STR);
		
		// test word sense
		WordSense ws = new WordSense(new Definition(DEF_STR), new PartOfSpeech(POS_STR));
		ws.addWordForm(wf);
		
		// test entry
		DictionaryEntry e = new DictionaryEntry(wf);
		e.addSense(ws);
		return e;
	}
	
	private static void assertTestEntry(DictionaryEntry e, DictionaryEntry re) {
		
		// Check id
		assertEquals(e.getId(), re.getId());
		
		// Check root word
		assertEquals(re.getWordRoot().getWordForm(), WF_STR);
		
		// Check word sense
		for (WordSense ws : re.getWordSenses()) {
			assertEquals(ws.getDefinition().getDefinition(), DEF_STR);
			assertEquals(ws.getPartOfSpeech().getPartOfSpeech(), POS_STR);
			for (WordForm wf : ws.getWorldForms()) {
				assertEquals(wf.getWordForm(), WF_STR);
			}
		}
	}

	@Test
	public void addRemoveTest() {
		
		/*
		 * Case 0: add entry.
		 */
		
		// Get a test entry and try to add it
		DictionaryEntry e = getTestEntry();
		Dictionary.getInstance().addEntry(e);
		
		// Make sure it was added correctly
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		DictionaryEntry re = (DictionaryEntry) session.get(DictionaryEntry.class, e.getId());
		assertTestEntry(e, re);
		session.getTransaction().commit();
		session.close();
		
		// Also look up and test that way
		re = getFirst(Dictionary.getInstance().lookupByEntry(WF_STR));
		assertTestEntry(e, re);
		
		/*
		 * Case 1: remove entry
		 */
		Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	@Test
	public void lookupByEntryTest() {
		
		// Load some entries to work with
		Dictionary.getInstance().addFromFile("entry_list.csv");
		Collection<DictionaryEntry> es = Dictionary.getInstance().getAllEntries();
		
		/*
		 * Case 0: lookup entry
		 */
		DictionaryEntry re = getFirst(Dictionary.getInstance().lookupByEntry("dance"));
		assertNotNull(re);
		// Make sure the wf matches
		assertEquals(re.getWordRoot().getWordForm(), "dance");
		
		/*
		 * Case 1: lookup a different entry
		 */
		re = getFirst(Dictionary.getInstance().lookupByEntry("gargle"));
		assertNotNull(re);
		// Make sure the wf matches
		assertEquals(re.getWordRoot().getWordForm(), "gargle");
		
		/*
		 * Case 2: lookup item that does not exist
		 */
		re = getFirst(Dictionary.getInstance().lookupByEntry("tomorrow"));
		assertNull(re);
		
		// Remove all from DB
		for (DictionaryEntry e : es)
			Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	@Test
	public void lookupByDefintionTest() {
		
		// Load some entries to work with
		Dictionary.getInstance().addFromFile("entry_list.csv");
		Collection<DictionaryEntry> es = Dictionary.getInstance().getAllEntries();
		
		/*
		 * Case 0: lookup entry
		 */
		DictionaryEntry re = getFirst(Dictionary.getInstance().lookupByDefinition(CHEESE_DEF_STR));
		assertNotNull(re);
		// Make sure the wf matches
		assertEquals(re.getWordRoot().getWordForm(), "cheese");
		
		/*
		 * Case 1: lookup a different entry
		 */
		re = getFirst(Dictionary.getInstance().lookupByDefinition(RUN_DEF_STR));
		assertNotNull(re);
		// Make sure the wf matches
		assertEquals(re.getWordRoot().getWordForm(), "run");
		
		/*
		 * Case 2: lookup item that does not exist
		 */
		re = getFirst(Dictionary.getInstance().lookupByDefinition("a thing"));
		assertNull(re);
		
		// Remove all from DB
		for (DictionaryEntry e : es)
			Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	@Test
	public void lookupByPartOfSpeechTest() {
		
		// Load entry
		DictionaryEntry e = getTestEntry();
		Dictionary.getInstance().addEntry(e);
		
		/*
		 * Case 0: lookup entry
		 */
		DictionaryEntry re = getFirst(Dictionary.getInstance().lookupByPartOfSpeech("Verb"));
		assertNotNull(re);
		// Make sure the wf matches
		assertEquals(re.getWordRoot().getWordForm(), "walk");
		
		/*
		 * Case 2: lookup item that does not exist
		 */
		re = getFirst(Dictionary.getInstance().lookupByPartOfSpeech("Noun"));
		assertNull(re);
		
		// Remove all from DB
		Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	@Test
	public void addSenseTest() {
		
		/*
		 * Case 0: get an entry then add to it. Make sure it made it to DB.
		 */
		DictionaryEntry e = getTestEntry();
		WordSense newWs = new WordSense(new Definition("supported by one's feet"), new PartOfSpeech("Verb"));
		newWs.addWordForm(new WordForm("Stand"));
		Dictionary.getInstance().addSense(newWs, e);
		
		// Remove from DB
		Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
		
	}
	
	@Test
	public void addFromFileTest() {
		
		/*
		 * Case 0: load from file and check
		 */
		Dictionary.getInstance().addFromFile("entry_list.csv");
		List<DictionaryEntry> es = Dictionary.getInstance().getAllEntries();
		
		// check count
		assertEquals(es.size(), 5);
		
		// get entries
		DictionaryEntry run = es.get(0);
		DictionaryEntry smile = es.get(1);
		DictionaryEntry dance = es.get(2);
		DictionaryEntry cheese = es.get(3);
		DictionaryEntry gargle = es.get(4);
		
		// check root words
		assertEquals(run.getWordRoot().getWordForm(), "run");
		assertEquals(smile.getWordRoot().getWordForm(), "smile");
		assertEquals(dance.getWordRoot().getWordForm(), "dance");
		assertEquals(cheese.getWordRoot().getWordForm(), "cheese");
		assertEquals(gargle.getWordRoot().getWordForm(), "gargle");
		
		// get word senses
		WordSense runSense = getFirst(run.getWordSenses());
		assertNotNull(runSense);
		WordSense smileSense = getFirst(smile.getWordSenses());
		assertNotNull(smileSense);
		WordSense danceSense = getFirst(dance.getWordSenses());
		assertNotNull(danceSense);
		WordSense cheeseSense = getFirst(cheese.getWordSenses());
		assertNotNull(cheeseSense);
		WordSense gargleSense = getFirst(gargle.getWordSenses());
		assertNotNull(gargleSense);
		
		// check POS's (they should be null)
		assertNull(runSense.getPartOfSpeech());
		assertNull(smileSense.getPartOfSpeech());
		assertNull(danceSense.getPartOfSpeech());
		assertNull(cheeseSense.getPartOfSpeech());
		assertNull(gargleSense.getPartOfSpeech());
		
		// check that the word sense also has the word forms
		assertEquals(getFirst(runSense.getWorldForms()).getWordForm(), "run");
		assertEquals(getFirst(smileSense.getWorldForms()).getWordForm(), "smile");
		assertEquals(getFirst(danceSense.getWorldForms()).getWordForm(), "dance");
		assertEquals(getFirst(cheeseSense.getWorldForms()).getWordForm(), "cheese");
		assertEquals(getFirst(gargleSense.getWorldForms()).getWordForm(), "gargle");
		
		// check defs
		Definition runDef = new Definition(RUN_DEF_STR);
		assertEquals(runSense.getDefinition().getDefinition(), runDef.getDefinition());
		Definition smileDef = new Definition(SMILE_DEF_STR);
		assertEquals(smileSense.getDefinition().getDefinition(), smileDef.getDefinition());
		Definition danceDef = new Definition(DANCE_DEF_STR);
		assertEquals(danceSense.getDefinition().getDefinition(), danceDef.getDefinition());
		Definition cheeseDef = new Definition(CHEESE_DEF_STR);
		assertEquals(cheeseSense.getDefinition().getDefinition(), cheeseDef.getDefinition());
		Definition gargleDef = new Definition(GARGLE_DEF_STR);
		assertEquals(gargleSense.getDefinition().getDefinition(), gargleDef.getDefinition());
		
		// Remove all from DB
		for (DictionaryEntry e : es)
			Dictionary.getInstance().removeDictionaryEntry(e.getWordRoot().getWordForm());
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}

}
