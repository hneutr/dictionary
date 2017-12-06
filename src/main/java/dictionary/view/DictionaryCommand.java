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
	
	public static final String DICTIONARY_ENTRY_QUERY_TYPE = "entry";
	public static final String WORD_SENSE_QUERY_TYPE = "wordsense";
	public static final String PART_OF_SPEECH_QUERY_TYPE = "partofspeech";
	public static final String DEFINITION_QUERY_TYPE = "definition";
	public static final String WORD_FORM_QUERY_TYPE = "wordform";
}
