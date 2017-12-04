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
			Dictionary.getInstance().lookupByEntry(this.query);
		}
	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
