package dictionary.driver;

import dictionary.view.*;

public class DemoTestDriver {
	public static void main(String[] args) {
		System.out.println("TEST");
		
		CommandInvoker commandRunner = new CommandInvoker();
		
		AbstractCommand command = null;
		
		command = new AddCommand("entry_list.csv", DictionaryCommand.FILE_QUERY_TYPE);
		commandRunner.addToQueue(command);
		
		command = new LookupCommand("cheese", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		commandRunner.addToQueue(command);
		
		command = new RemoveCommand("dance", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		commandRunner.addToQueue(command);
		
		command = new LookupCommand("dance", DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		commandRunner.addToQueue(command);
	}
}
