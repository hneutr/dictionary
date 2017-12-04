package dictionary.view;

import dictionary.controller.Dictionary;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class AddCommand extends DictionaryCommand {
	private String fileName;
	private String additionType;
	
	public AddCommand(String fileName) {
		this.fileName = fileName;
		this.additionType = "FromFile";
	}
	
	@Override
	public void run() {
		if (this.additionType.equals("FromFile"))
			Dictionary.getInstance().addFromFile(this.fileName);
	}
	
	@Override
	public void displayStatus() {
		// TODO Auto-generated method stub
	}
}
