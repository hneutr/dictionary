package dictionary.view;

import dictionary.model.DictionaryEntry;
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
		this.queryType = queryType;
	}

	@Override
	void run() {
		switch (queryType) {
			case DICTIONARY_ENTRY_QUERY_TYPE: 
				entries = Dictionary.getInstance().lookupByEntry(query);
				break;
			case DEFINITION_QUERY_TYPE: 
				entries = Dictionary.getInstance().lookupByDefinition(query);
				break;
			case PART_OF_SPEECH_QUERY_TYPE: 
				entries = Dictionary.getInstance().lookupByPartOfSpeech(query);
				break;
			default: 
				break;
		}
			
		if (entries.size() != 0){
			for (DictionaryEntry entry : entries){
				System.out.println(entry.toString());					
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
