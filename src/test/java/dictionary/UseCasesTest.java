package dictionary;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.model.DictionaryEntry;
import dictionary.model.WordSense;
import dictionary.view.AbstractCommand;
import dictionary.view.AddCommand;
import dictionary.view.CommandInvoker;
import dictionary.view.DictionaryCommand;
import dictionary.view.RemoveCommand;
import dictionary.view.UpdateCommand;

/**
 * @author alexkillian
 *
 * The purpose of this set of unit tests is to make sure that all use cases work and are 
 * accessible through the commands. Thus it also tests the commands.
 */
public class UseCasesTest {

	private static final CommandInvoker cmdInvoke = new CommandInvoker();
	
	private static final String TEST0_ENTRY_WORD = "test";
	private static final String TEST0_ENTRY_WS_DEF = "to see if something works";
	private static final String TEST0_ENTRY_WS_WF = "testing";
	
	private static <T> T getFirst(Collection<T> list) {
		for (T item : list) return item;
		return null;
	}
	
	// Adds a test entry via the AddCommand
	private static void addTestEntry() {
		AbstractCommand addCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(addCmd);
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
	}
	
	// Removes a test entry via the RemoveCommand
	private static void removeTestEntry() {
		AbstractCommand removeCmd = new RemoveCommand(TEST0_ENTRY_WORD, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
	    assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-01 - User can add an entry.
	@Test
	public void addEntryTest() {
		// Add via command
		addTestEntry();
		
		// Check
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD));
		assertNotNull(e);
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		removeTestEntry();
	}
	
	// UR-02 - User can add a Word Sense to an Entry.
	@Test
	public void addWordSenseTest() {
		// Add via command
		addTestEntry();
		
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
		removeTestEntry();
	}
	
	// UR-03 - User can add a Definition to a Word Sense.
	@Test
	public void addDefinitionTest() {
		// Add via command
		addTestEntry();
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a def and check 
		AbstractCommand addDefCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.DEFINITION_QUERY_TYPE, 0, TEST0_ENTRY_WS_DEF);
		cmdInvoke.addToQueue(addDefCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals(TEST0_ENTRY_WS_DEF, ws.getDefinition().getDefinition());
		
		// Remove
		removeTestEntry();
	}
	
	// UR-04 - User can add a Word Form to a Word Sense.
	@Test
	public void addWordFormTest() {
		// Add via command
		addTestEntry();
		
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
		removeTestEntry();
	}
	
	// UR-05 - User can add a POS to a Word Sense.
	@Test
	public void addPOSTest() {
		// Add via command
		addTestEntry();
		
		// Add a word sense
		AbstractCommand addSenseCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		
		// Add a POS and check 
		AbstractCommand addPOSCmd = new AddCommand(TEST0_ENTRY_WORD, DictionaryCommand.PART_OF_SPEECH_QUERY_TYPE, 0, "Verb");
		cmdInvoke.addToQueue(addPOSCmd);
		WordSense ws = getFirst(getFirst(Dictionary.getInstance().lookupByEntry(TEST0_ENTRY_WORD)).getWordSenses());
		assertEquals("Verb", ws.getPartOfSpeech().getPartOfSpeech());
		
		// Remove
		removeTestEntry();
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
		addTestEntry();
		
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
	
	// UR-14
	@Test
	public void removeEntryTest() {
		
	}
	
	// UR-15
	@Test
	public void removeWordSenseTest() {
		
	}
	
	// UR-16
	@Test
	public void removePartOfSpeechTest() {
		
	}
	
	// UR-17
	@Test
	public void removeDefintionTest() {
		
	}
	
	// UR-18
	@Test
	public void removeWordFormTest() {
		
	}
}
