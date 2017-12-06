package dictionary.view;

import dictionary.controller.Dictionary;
import dictionary.model.DictionaryEntry;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class RemoveCommand extends DictionaryCommand {
	private String query;
	private Collection<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	public RemoveCommand(String query) {
		this.query = query;
	}

	@Override
	void run() {
		this.entries = Dictionary.getInstance().lookupByEntry(this.query); 
		
		if (entries.size() != 0){
			for (DictionaryEntry entry : entries){
				Dictionary.getInstance().remove(entry);					
			}
		} else {
			System.out.print("No entries found.");
		}
	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
