package dictionary.view;

import dictionary.controller.Dictionary;
import dictionary.model.Definition;
import dictionary.model.DictionaryEntry;
import dictionary.model.PartOfSpeech;
import dictionary.model.WordForm;
import dictionary.model.WordSense;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class AddCommand extends DictionaryCommand {
	private String queryType;
	private String query;
	private String target;
	private int idx;
	
	public AddCommand(String query, String queryType) {
		this.query = query;
		this.queryType = queryType;
	}
	
	public AddCommand(String query, String queryType, int idx, String target) {
		this(query, queryType);
		this.idx = idx;
		this.target = target;
	}
	
	@Override
	public void run() {
		switch (queryType) {
			case FILE_QUERY_TYPE:
				Dictionary.getInstance().addFromFile(this.query);
				break;
			case DICTIONARY_ENTRY_QUERY_TYPE:
				Dictionary.getInstance().addDictionaryEntry(this.query);
				break;
			case WORD_SENSE_QUERY_TYPE:
				Dictionary.getInstance().addWordSense(this.query);
				break;
			case DEFINITION_QUERY_TYPE:
				Dictionary.getInstance().addDefinition(this.query, this.idx, this.target);
				break;
			case PART_OF_SPEECH_QUERY_TYPE:
				Dictionary.getInstance().addPartOfSpeech(this.query, this.idx, this.target);
				break;
			case WORD_FORM_QUERY_TYPE:
				Dictionary.getInstance().addWordForm(this.query, this.idx, this.target);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void displayStatus() {
		// TODO Auto-generated method stub
	}
}
