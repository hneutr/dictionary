package dictionary.model;

/**
 * 
 * @author Adam Wiemerslage
 *
 * This class represents a definition in a dictionary entry.
 */
public class Definition implements ICollide<Definition> {
	
	/**
	 * The Definition text
	 */
	private String definition;
	
	public String getDefinition() {
		return definition;
	}
	
	public void setDefinition(String def) {
		definition = def;
	}
	
	public boolean collides(Definition def) {
		return def.getDefinition().equals(getDefinition());
	}
}