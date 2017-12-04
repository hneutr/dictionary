package dictionary.view;

import dictionary.controller.Dictionary;
import dictionary.model.DictionaryEntry;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class RemoveCommand extends DictionaryCommand {
	private String query;
	
	public RemoveCommand(String query) {
		this.query = query;
	}

	@Override
	void run() {
		this.entry = Dictionary.getInstance().lookupByEntry(this.query);
		Dictionary.getInstance().remove(this.entry);
	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
