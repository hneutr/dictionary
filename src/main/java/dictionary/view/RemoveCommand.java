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
	private String queryType;
	private int idx;
	private String wordForm;
	private Collection<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	
	public RemoveCommand(String query, String queryType) {
		this.query = query;
		this.queryType = queryType;
	}

	public RemoveCommand(String query, int idx, String queryType) {
		this(query, queryType);
		this.idx = idx;
	}

	public RemoveCommand(String query, int idx, String queryType, String wordForm) {
		this(query, idx, queryType);
		this.wordForm = wordForm;
	}
	
	@Override
	void run() {
		this.entries = Dictionary.getInstance().lookupByEntry(this.query); 
		
		if (queryType.equals(DICTIONARY_ENTRY_QUERY_TYPE)) {
			if (entries.size() != 0){
				for (DictionaryEntry entry : entries){
					Dictionary.getInstance().remove(entry);					
				}
			} else {
				System.out.print("No entries found.");
			}	
		}
		else if (queryType.equals(DEFINITION_QUERY_TYPE)) {
			Dictionary.getInstance().removeDefinition(query, idx);			
		}
		

	}

	@Override
	void displayStatus() {
		// TODO Auto-generated method stub

	}

}
