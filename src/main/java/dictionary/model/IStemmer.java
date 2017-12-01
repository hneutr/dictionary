package dictionary.model;

/**
 * 
 * @author Adam Wiemerslage
 *
 * Interface that gets the stem from a word form
 */
public interface IStemmer {
	
	public String stem(String s);
}
