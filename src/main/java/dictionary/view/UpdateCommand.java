package dictionary.view;

import dictionary.controller.Dictionary;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class UpdateCommand extends DictionaryCommand {
	private String queryType;
	private String query;
	private String target;
	private String oldWordForm;
	private String status;
	private int idx;
	
	public UpdateCommand(String query, String queryType) {
		this.query = query;
		this.queryType = queryType;
	}
	
	public UpdateCommand(String query, String queryType, String target) {
		this(query, queryType);
		this.target = target;
	}
	
	public UpdateCommand(String query, String queryType, int idx, String target) {
		this(query, queryType);
		this.idx = idx;
		this.target = target;
	}
	
	public UpdateCommand(String query, String queryType, int idx, String oldWordForm, String target) {
		this(query, queryType, idx, target);
		this.oldWordForm = oldWordForm;
	}
	
	@Override
	void run() {
		switch (queryType) {
			case DICTIONARY_ENTRY_QUERY_TYPE: 
				Dictionary.getInstance().updateDictionaryEntry(query, target);
				status = getIdentifierString(query) + ": set Word Root to " + target;
				break;
			case DEFINITION_QUERY_TYPE: 
				Dictionary.getInstance().updateDefinition(query, idx, target);
				status = getIdentifierString(query, idx) + ": set Definition to " + target;
				break;
			case PART_OF_SPEECH_QUERY_TYPE: 
				Dictionary.getInstance().updatePartOfSpeech(query, idx, target);
				status = getIdentifierString(query, idx) + ": set Part of Speech to " + target;
				break;
			case WORD_FORM_QUERY_TYPE:
				Dictionary.getInstance().updateWordForm(query, idx, oldWordForm, target);
				status = getIdentifierString(query, idx, oldWordForm) + ": set Word Form to " + target;
				break;
			default: 
				setNotSupportedStatus(queryType);
				break;
		}
	}
}
