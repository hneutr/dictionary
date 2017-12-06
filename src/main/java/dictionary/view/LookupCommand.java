package dictionary.view;

import dictionary.model.DictionaryEntry;
import dictionary.model.WordForm;
import dictionary.controller.Dictionary;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class LookupCommand extends DictionaryCommand {
	private String query;
	private String queryType;
	private Collection<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	public LookupCommand(String query, String queryType) {
		this.query = query;
		this.queryType = "WordForm";
	}

	@Override
	void run() {
		if (this.queryType.equals("WordForm")) {
			entries = Dictionary.getInstance().lookupByEntry(this.query);

			if (entries.size() != 0){
				for (DictionaryEntry entry : entries){
					System.out.println(entry.toString());					
				}
			} else {
				System.out.print("No entries found.");
			}
		}
	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
