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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static DictionaryEntry getTestEntry() {
		
		// test word form
		WordForm wf = new WordForm("test");
		
		// test word sense
		WordSense ws = new WordSense(new Definition("test"), new PartOfSpeech("Noun"));
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
	}

	@Test
	public void addEntryTest() {
		
		// Get a test entry and try to add it
		DictionaryEntry e = getTestEntry();
		Dictionary.getInstance().addEntry(e);
		
		// Make sure it was added correctly
		checkTestEntry(e);
	}

}
