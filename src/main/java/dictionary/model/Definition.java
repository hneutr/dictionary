package dictionary.model;

import javax.persistence.Embeddable;

/**
 * 
 * @author Adam Wiemerslage
 *
 * This class represents a definition in a dictionary entry.
 */
@Embeddable
public class Definition implements ICollide<Definition> {
	
	/**
	 * The Definition text
	 */
	private String definition;
	
	public Definition() {
	}
	
	/**
	 * Constructor for definition.
	 * 
	 * @param definition
	 */
	public Definition(String definition) {
		this.definition = definition;
	}
	
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