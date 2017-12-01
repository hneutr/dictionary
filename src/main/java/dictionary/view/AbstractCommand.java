package dictionary.view;

public abstract class AbstractCommand {
	private String name;
	private String shortDescription;
	private String longDescription;
	
	abstract void run();
	abstract void displayStatus();
}
