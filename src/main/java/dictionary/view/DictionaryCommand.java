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
}
