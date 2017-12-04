import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

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
		DictionaryEntry e = new DictionaryEntry(wf);
		e.addSense(ws);
		return e;
	}
	
	private static void assertTestEntry(DictionaryEntry e, DictionaryEntry re) {
		
		// Check id
		assertEquals(e.getId(), re.getId());
		
		// Check root word
		assertEquals(re.getWordRoot().getWordForm(), WF.getWordForm());
		
		// Check word sense
		for (WordSense ws : re.getWordSenses()) {
			for (WordForm wf : ws.getWorldForms()) {
				assertEquals(wf.getWordForm(), WF.getWordForm());
			}
		}
	}

	@Test
	public void addRemoveLookupTest() {
		
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
		
		/*
		 * Case 1: lookup entry
		 */
		re = Dictionary.getInstance().lookupByEntry("test");
		assertNotNull(re);
		
		/*
		 * Case 2: remove entry
		 */
		Dictionary.getInstance().remove(e);
	}
	
	private static WordSense getFirst(Collection<WordSense> senses) {
		for (WordSense ws : senses) return ws;
		return null;
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
		
		// check defs
		Definition runDef = new Definition("move at a speed faster than a walk and never have both or all the feet on the ground at the same time.");
		assertEquals(getFirst(run.getWordSenses()).getDefinition().getDefinition(), runDef.getDefinition());
		
		Definition smileDef = new Definition("form one's features into a pleased or kind or amused expression");
		assertEquals(getFirst(smile.getWordSenses()).getDefinition().getDefinition(), smileDef.getDefinition());
		
		Definition danceDef = new Definition("move rhythmically to music");
		assertEquals(getFirst(dance.getWordSenses()).getDefinition().getDefinition(), danceDef.getDefinition());
	
		Definition cheeseDef = new Definition("a food made from the pressed curds of milk");
		assertEquals(getFirst(cheese.getWordSenses()).getDefinition().getDefinition(), cheeseDef.getDefinition());
		
		Definition gargleDef = new Definition("wash one's mouth and throat with a liquid kept in motion by exhaling through it.");
		assertEquals(getFirst(gargle.getWordSenses()).getDefinition().getDefinition(), gargleDef.getDefinition());
	}

}
