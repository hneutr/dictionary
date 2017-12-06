package dictionary;

import static org.junit.Assert.*;

import org.junit.Test;

import dictionary.controller.Dictionary;
import dictionary.view.AbstractCommand;
import dictionary.view.AddCommand;
import dictionary.view.CommandInvoker;
import dictionary.view.RemoveCommand;

public class UseCasesTest {

	@Test
	public void addRemoveSingleEntryTest() {
		
		// Add via command
		AbstractCommand addCmd = new AddCommand("test","to see if something works", "Verb");
		CommandInvoker cmdInvoke = new CommandInvoker();
		cmdInvoke.addToQueue(addCmd);
		
		// Check
		assertEquals(Dictionary.getInstance().getAllEntries().size(), 1);
		
		// Remove
		AbstractCommand removeCmd = new RemoveCommand("test");
		cmdInvoke.addToQueue(removeCmd);
		
		// Check
	    assertEquals(Dictionary.getInstance().getAllEntries().size(), 0);
	}
}
