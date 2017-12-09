package dictionary;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.model.DictionaryEntry;
import dictionary.model.WordForm;
import dictionary.model.WordSense;
import dictionary.view.AbstractCommand;
import dictionary.view.AddCommand;
import dictionary.view.CommandInvoker;
import dictionary.view.DictionaryCommand;
import dictionary.view.RemoveCommand;
import dictionary.view.UpdateCommand;

/**
 * @author Alex Killian
 *
 * The purpose of this set of unit tests is to make sure that all use cases work and are 
 * accessible through the commands. Thus it also tests the commands. The tests overlap some.
 */
public class UseCasesTest {

	private static final CommandInvoker cmdInvoke = new CommandInvoker();
	
	// Properties for entry 0
	private static final String TEST0_ENTRY_WORD = "test";
	private static final String TEST0_ENTRY_WS_DEF = "to see if something works";
	private static final String TEST0_ENTRY_WS_WF = "testing";
	private static final String TEST0_ENTRY_WS_POS = "Verb";
	
	// Properties for entry 1
	private static final String TEST1_ENTRY_WORD = "river";
	private static final String TEST1_ENTRY_WS_DEF = "a large stream of water";
	private static final String TEST1_ENTRY_WS_WF = "rivers";
	private static final String TEST1_ENTRY_WS_POS = "Noun";
	
	private static <T> T getFirst(Collection<T> list) {
		for (T item : list) return item;
		return null;
	}
	
	// Adds test0 entry via the AddCommand
	private static void addTest0Entry() {
		AbstractCommand addCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(addCmd);
	}
	
	// Removes test0 entry via the RemoveCommand
	private static void removeTest0Entry() {
		AbstractCommand removeCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
	}
	
	// Adds test1 entry via the AddCommand
	private static void addTest1Entry() {
		AbstractCommand addCmd = new AddCommand(TEST1_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(addCmd);
	}
	
	// Removes test1 entry via the RemoveCommand
	private static void removeTest1Entry() {
		AbstractCommand removeCmd = new RemoveCommand(TEST1_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
	}
	
	// UR-01 - User can add an entry.
	@Test
	public void addEntryTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Check
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertNotNull(e);
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-02 - User can add a Word Sense to an Entry.
	@Test
	public void addWordSenseTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Check (we expect that the entry does not have a sense yet)
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertEquals(e.getWordSenses().size(), 0);
		
		// Add word sense via command and check (we expect there to be one word sense)
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertNotNull(e);
		assertEquals(1, e.getWordSenses().size());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-03 - User can add a Definition to a Word Sense.
	@Test
	public void addDefinitionTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a def and check 
		AbstractCommand addDefCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, TEST0_ENTRY_WS_DEF);
		cmdInvoke.addToQueue(addDefCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals(TEST0_ENTRY_WS_DEF, ws.getDefinition().getDefinition());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-04 - User can add a Word Form to a Word Sense.
	@Test
	public void addWordFormTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a word form and check 
		AbstractCommand addWFCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_FORM_QUERY_TYPE, 0, TEST0_ENTRY_WS_WF);
		cmdInvoke.addToQueue(addWFCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals(1, ws.getWorldForms().size());
		assertEquals(TEST0_ENTRY_WS_WF, getFirst(ws.getWorldForms()).getWordForm());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-05 - User can add a POS to a Word Sense.
	@Test
	public void addPOSTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a POS and check 
		AbstractCommand addPOSCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, "Verb");
		cmdInvoke.addToQueue(addPOSCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals("Verb", ws.getPartOfSpeech().getPartOfSpeech());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-06 - User can add entries in batch (from file)
	@Test
	public void addEntriesInBatchTest() {
		// Add via command
		AbstractCommand addCmd = new AddCommand("entry_list.csv", DictionaryCommand.FILE_QUERY_TYPE);
		cmdInvoke.addToQueue(addCmd);
		
		// Basic check (since the Dictionary unit tests this too)
		assertEquals(5, Dictionary.getInstance().getAllEntries().size());
		
		// Remove
		cmdInvoke.addToQueue(new RemoveCommand("run", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE));
		cmdInvoke.addToQueue(new RemoveCommand("smile", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE));
		cmdInvoke.addToQueue(new RemoveCommand("dance", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE));
		cmdInvoke.addToQueue(new RemoveCommand("cheese", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE));
		cmdInvoke.addToQueue(new RemoveCommand("gargle", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE));
	    assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-07 - User can update an Entry word spelling.
	@Test
	public void updateEntryTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Update and check
		AbstractCommand updateCmd = new UpdateCommand(TEST0_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE, "tested");
		cmdInvoke.addToQueue(updateCmd);
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry("tested"));
		assertNotNull(e);
		
		// Remove
		AbstractCommand removeCmd = new RemoveCommand("tested", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-08 - User can update a word form spelling.
	@Test
	public void updateWordFormTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a word form
		AbstractCommand addWFCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_FORM_QUERY_TYPE, 0, "a old wf");
		cmdInvoke.addToQueue(addWFCmd);
		
		// Update and check
		AbstractCommand updateCmd = new UpdateCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_FORM_QUERY_TYPE, 0, "a old wf", "a new wf");
		cmdInvoke.addToQueue(updateCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		WordForm wf = getFirst(ws.getWorldForms());
		assertNotNull(wf);
		assertEquals("a new wf", wf.getWordForm());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-09 - User can update an update a definition.
	@Test
	public void updateDefinitionTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Update and check
		AbstractCommand updateCmd = new UpdateCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, "a new def");
		cmdInvoke.addToQueue(updateCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals("a new def", ws.getDefinition().getDefinition());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-10 - User can update an update a POS.
	@Test
	public void updatePOSTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Update and check
		AbstractCommand updateCmd = new UpdateCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, "Noun");
		cmdInvoke.addToQueue(updateCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals("Noun", ws.getPartOfSpeech().getPartOfSpeech());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-11 - User can lookup Entries by word.
	@Test
	public void lookupByEntryTest() {
		// Add via command
		addTest0Entry();
		addTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 2);
		
		// Check
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertNotNull(e);
		assertEquals(TEST0_ENTRY_WORD, e.getWordRoot().getWordForm());
		e = getFirst(Dictionary.getInstance().lookupByEntry(TEST1_ENTRY_WORD));
		assertNotNull(e);
		assertEquals(TEST1_ENTRY_WORD, e.getWordRoot().getWordForm());
		
		// Lookup by something that does not exist
		e = getFirst(Dictionary.getInstance().lookupByEntry("time"));
		assertNull(e);
		
		// Remove
		removeTest0Entry();
		removeTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-12 - User can lookup Entries by definition.
	@Test
	public void lookupByDefinitionTest() {
		// Add via command
		addTest0Entry();
		addTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 2);
		
		// Add a word sense (test0)
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a definition (test0)
		AbstractCommand addDefCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, TEST0_ENTRY_WS_DEF);
		cmdInvoke.addToQueue(addDefCmd);
		
		// Add a word sense (test1)
		addSenseCmd = new AddCommand(TEST1_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a definition (test1)
		addDefCmd = new AddCommand(TEST1_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, TEST1_ENTRY_WS_DEF);
		cmdInvoke.addToQueue(addDefCmd);
		
		// Lookup def and check 
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByDefinition(TEST0_ENTRY_WS_DEF));
		assertEquals(TEST0_ENTRY_WORD, e.getWordRoot().getWordForm());
		e = getFirst(Dictionary.getInstance().lookupByDefinition(TEST1_ENTRY_WS_DEF));
		assertEquals(TEST1_ENTRY_WORD, e.getWordRoot().getWordForm());
		
		// Lookup fake
		e = getFirst(Dictionary.getInstance().lookupByDefinition("time"));
		assertNull(e);
		
		// Remove
		removeTest0Entry();
		removeTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-13 - User can lookup Entries by POS.
	@Test
	public void lookupByPOSTest() {
		// Add via command
		addTest0Entry();
		addTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 2);
		
		// Add a word sense (test0)
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a POS (test0)
		AbstractCommand addPOSCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, TEST0_ENTRY_WS_POS);
		cmdInvoke.addToQueue(addPOSCmd);
		
		// Add a word sense (test1)
		addSenseCmd = new AddCommand(TEST1_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a POS (test1)
		addPOSCmd = new AddCommand(TEST1_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, TEST1_ENTRY_WS_POS);
		cmdInvoke.addToQueue(addPOSCmd);
		
		// Lookup POS and check 
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByPartOfSpeech(TEST0_ENTRY_WS_POS));
		assertEquals(TEST0_ENTRY_WORD, e.getWordRoot().getWordForm());
		e = getFirst(Dictionary.getInstance().lookupByPartOfSpeech(TEST1_ENTRY_WS_POS));
		assertEquals(TEST1_ENTRY_WORD, e.getWordRoot().getWordForm());
		
		// Lookup fake
		e = getFirst(Dictionary.getInstance().lookupByPartOfSpeech("time"));
		assertNull(e);
		
		// Remove
		removeTest0Entry();
		removeTest1Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-14 - User can remove an Entry.
	@Test
	public void removeEntryTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-15 - User can remove a Word Sense.
	@Test
	public void removeWordSenseTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertEquals(1, e.getWordSenses().size());
		
		// Remove sense and check
		AbstractCommand removeSenseCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE, 0);
		cmdInvoke.addToQueue(removeSenseCmd);
		e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertEquals(0, e.getWordSenses().size());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-16 - User can remove a POS.
	@Test
	public void removePartOfSpeechTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a POS
		AbstractCommand addPOSCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, TEST0_ENTRY_WS_POS);
		cmdInvoke.addToQueue(addPOSCmd);
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByPartOfSpeech(TEST0_ENTRY_WS_POS));
		assertEquals(TEST0_ENTRY_WS_POS, getFirst(e.getWordSenses()).getPartOfSpeech().getPartOfSpeech());
		
		// Remove POS and check
		AbstractCommand removePOSCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0);
		cmdInvoke.addToQueue(removePOSCmd);
		e = getFirst(Dictionary.getInstance().lookupByPartOfSpeech(TEST0_ENTRY_WS_POS));
		assertNull(e);
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-17 - User can remove a Definition
	@Test
	public void removeDefintionTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a def
		AbstractCommand addDefCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, TEST0_ENTRY_WS_DEF);
		cmdInvoke.addToQueue(addDefCmd);
		
		// Remove def and check
		AbstractCommand removeDefCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0);
		cmdInvoke.addToQueue(removeDefCmd);
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByDefinition(TEST0_ENTRY_WS_DEF));
		assertNull(e);
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-18 - User can remove a word form
	@Test
	public void removeWordFormTest() {
		// Add via command
		addTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Add word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a word form
		AbstractCommand addDefCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_FORM_QUERY_TYPE, 0, TEST0_ENTRY_WS_WF);
		cmdInvoke.addToQueue(addDefCmd);
		
		// Remove def and check
		AbstractCommand removeWFCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_FORM_QUERY_TYPE, 0, TEST0_ENTRY_WS_WF);
		cmdInvoke.addToQueue(removeWFCmd);
		WordSense e = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals(0, e.getWorldForms().size());
		
		// Remove
		removeTest0Entry();
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
}
