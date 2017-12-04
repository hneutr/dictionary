package dictionary.view;

import dictionary.model.WordForm;
import dictionary.controller.Dictionary;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class LookupCommand extends DictionaryCommand {
	private String query;
	private String queryType;
	
	public LookupCommand(String query, String queryType) {
		this.query = query;
		this.queryType = "WordForm";
	}

	@Override
	void run() {
		if (this.queryType.equals("WordForm")) {
			entry = Dictionary.getInstance().lookupByEntry(this.query);
			
			if (entry != null) {
				System.out.println(entry.toString());
			} else {
				System.out.print("No entry found.");
			}
		}
	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
