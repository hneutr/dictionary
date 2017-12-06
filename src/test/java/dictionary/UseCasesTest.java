package dictionary;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.model.DictionaryEntry;
import dictionary.view.AbstractCommand;
import dictionary.view.AddCommand;
import dictionary.view.CommandInvoker;
import dictionary.view.DictionaryCommand;
import dictionary.view.RemoveCommand;

public class UseCasesTest {

	private static <T> T getFirst(Collection<T> list) {
		for (T item : list) return item;
		return null;
	}
	
	// UR-01
	@Test
	public void addEntryTest() {
		
		// Add via command
		AbstractCommand addCmd = new AddCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		CommandInvoker cmdInvoke = new CommandInvoker();
		cmdInvoke.addToQueue(addCmd);
		
		// Check
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry("test"));
		assertNotNull(e);
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		AbstractCommand removeCmd = new RemoveCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
		
		// Check
	    assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
	
	// UR-02
	@Test
	public void addWordSense() {
		
		// Add via command
		AbstractCommand addCmd = new AddCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		CommandInvoker cmdInvoke = new CommandInvoker();
		cmdInvoke.addToQueue(addCmd);
		
		// Check
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		DictionaryEntry e = getFirst(Dictionary.getInstance().lookupByEntry("test"));
		assertNotNull(e);
		assertEquals(e.getWordSenses().size(), 0);
		
		// Add word sense via command
		AbstractCommand addSenseCmd = new AddCommand("wordSense", DictionaryCommand.WORD_SENSE_QUERY_TYPE);
		cmdInvoke.addToQueue(addSenseCmd);
		e = getFirst(Dictionary.getInstance().lookupByEntry("test"));
		assertNotNull(e);
		//assertEquals(e.getWordSenses().size(), 1);
		
		// Remove
		/*AbstractCommand removeCmd = new RemoveCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
		
		// Check
	    assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);*/
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
