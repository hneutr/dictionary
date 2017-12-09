package dictionary.view;

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
	private Collection<?> entries = new ArrayList<>();
	
	public LookupCommand(String query, String queryType) {
		this.query = query;
		this.queryType = queryType;
	}

	@Override
	void run() {
		boolean validQuery = true;
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
				setNotSupportedStatus(queryType);
				validQuery = false;
				break;
		}
		
		if (validQuery) {
			if (entries.size() != 0){
				status = "";
				for (Object entry : entries){
					status = status + entry.toString();				
				}
			} else {
				status = "No entries found.";
			}
		}
	}
}
