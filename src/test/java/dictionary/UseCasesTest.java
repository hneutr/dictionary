package dictionary;

import static org.junit.Assert.*;

import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.view.AbstractCommand;
import dictionary.view.AddCommand;
import dictionary.view.CommandInvoker;
import dictionary.view.DictionaryCommand;
import dictionary.view.RemoveCommand;

public class UseCasesTest {

	// UR-01
	@Test
	public void addSingleEntryTest() {
		
		// Add via command
		AbstractCommand addCmd = new AddCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		CommandInvoker cmdInvoke = new CommandInvoker();
		cmdInvoke.addToQueue(addCmd);
		
		// Check
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		AbstractCommand removeCmd = new RemoveCommand("test", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		cmdInvoke.addToQueue(removeCmd);
		
		// Check
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
