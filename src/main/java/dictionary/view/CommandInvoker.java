package dictionary.view;

/**
 * 
 * @author Hunter Wapman
 *
 */
public class CommandInvoker {
	public void addToQueue(AbstractCommand command) {
		command.run();
	}
}
