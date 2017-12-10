package dictionary.driver;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;

import dictionary.view.*;

/**
 * A demo of the dictionary application.
 * 
 * The dictionary does not have a front-end, so this
 * serves as a basic user interface for the demo only.
 * 
 * Covers three basic/essential use cases:
 * 	1. Adding entries from file
 * 	2. Looking up entries
 *  3. Removing entries
 */
public class DemoTestDriver {
	
	private static CommandInvoker commandRunner = new CommandInvoker();
	private static Scanner in = new Scanner(System.in); // used to pause the demo until enter is pressed
	
	private static void loadFromFile() {
		AbstractCommand command = new AddCommand("entry_list.csv", DictionaryCommand.FILE_QUERY_TYPE);
		commandRunner.addToQueue(command);
	}
	
	private static void lookup(String word) {
		AbstractCommand  command = new LookupCommand(word, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		commandRunner.addToQueue(command);
	}
	
	private static void remove(String word) {
		AbstractCommand command = new RemoveCommand(word, DictionaryCommand.DICTIONARY_ENTRY_QUERY_TYPE);
		commandRunner.addToQueue(command);
	}
	
	private static void disableHibernateLogging() {
		LogManager lm = LogManager.getLogManager();
		lm.getLogger("").setLevel(Level.OFF);
	}
	
	public static void main(String[] args) {
		
		disableHibernateLogging();
		
		// Print welcome message
		System.out.println("Welcome to a simple demo of the Multifaceted Dictionary!");
		System.out.println("This demo will show three use cases. Press enter to advance throughout.");
		System.out.println("");
		
		// First use case
		in.nextLine();
		System.out.println("---First: Let's add entries from a CSV file.---");
		in.nextLine();
		System.out.println("Loading entries from file...");
		loadFromFile();
		System.out.println("");
		
		// Second use case
		in.nextLine();
		System.out.println("---Second: Let's look up some of the entries that we just added.---");
		in.nextLine();
		System.out.println("Let's look up cheese");
		in.nextLine();
		lookup("cheese");
		System.out.println("");
		in.nextLine();
		System.out.println("Let's look up run");
		in.nextLine();
		lookup("run");
		System.out.println("");
		
		// Third use case
		in.nextLine();
		System.out.println("---Third: Let's remove all the entries that we added.---");
		in.nextLine();
		remove("run");
		remove("smile");
		remove("dance");
		remove("cheese");
		remove("gargle");
		
		// Back to second use case example
		in.nextLine();
		System.out.println("---Finally: Let's look up an entry we just removed to make sure it's gone.---");
		in.nextLine();
		System.out.println("Let's look up run");
		in.nextLine();
		lookup("run");
		
		in.close();
	}
}
