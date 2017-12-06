package dictionary.view;

public abstract class AbstractCommand {
	private String name;
	private String shortDescription;
	private String longDescription;
	
	public void execute() {
		run();
		displayStatus();
	}
	
	abstract void run();
	abstract void displayStatus();
}
