package dictionary.view;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class CommandInvoker {
	private double version = 1.0;
	
	public void addToQueue(AbstractCommand command) {
		command.execute();
	}
}
