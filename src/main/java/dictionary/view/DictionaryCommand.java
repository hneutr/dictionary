package dictionary.view;

import dictionary.model.*;

/**
 * 
 * @author Hunter Wapman
 *
 */
public abstract class DictionaryCommand extends AbstractCommand {
	// stuff for later
	protected DictionaryEntry entry;
	protected Definition definition;
	protected PartOfSpeech partOfSpeech;
	protected WordForm wordForm;
	protected WordSense wordSense;
	protected String status;
	
	public static final String FILE_QUERY_TYPE = "file";
	public static final String DICTIONARY_ENTRY_QUERY_TYPE = "entry";
	public static final String WORD_SENSE_QUERY_TYPE = "wordsense";
	public static final String PART_OF_SPEECH_QUERY_TYPE = "partofspeech";
	public static final String DEFINITION_QUERY_TYPE = "definition";
	public static final String WORD_FORM_QUERY_TYPE = "wordform";
	
	public void displayStatus(){
		System.out.println(status);
	}
	
	protected void setNotSupportedStatus(String queryType) {
		this.status = queryType + " is not supported";
	}
	
	protected String getIdentifierString(String query) {
		return "Entry " + query;
	}

	protected String getIdentifierString(String query, int idx) {
		return getIdentifierString(query) + ", Word Sense " + idx;
	}

	protected String getIdentifierString(String query, int idx, String wordForm) {
		return getIdentifierString(query, idx) + ", Word Form " + wordForm;
	}

}
