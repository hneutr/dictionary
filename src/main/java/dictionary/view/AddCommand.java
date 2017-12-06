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
	private String fileName;
	private String additionType;
	private String word;
	private String def;
	private String pos;
	
	public AddCommand(String fileName) {
		this.fileName = fileName;
		this.additionType = "FromFile";
	}
	
	public AddCommand(String word, String def, String pos) {
		this.word = word;
		this.def = def;
		this.pos = pos;
	}
	
	@Override
	public void run() {
		if (fileName != null) {
			if (this.additionType.equals("FromFile"))
				Dictionary.getInstance().addFromFile(this.fileName);
		}
		else {
			WordForm wf = new WordForm(word);
			WordSense ws = new WordSense(new Definition(def), new PartOfSpeech(pos));
			ws.addWordForm(wf);
			DictionaryEntry e = new DictionaryEntry(wf);
			e.addSense(ws);
			Dictionary.getInstance().addEntry(e);
		}
	}
	
	@Override
	public void displayStatus() {
		// TODO Auto-generated method stub
	}
}
