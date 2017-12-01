package dictionary.model;

import edu.stanford.nlp.process.*;

/**
 * 
 * @author Adam Wiemerslage
 *
 * The porter stemmer implementation for stemming
 */
public class PorterStemmer implements IStemmer{
	int version;
	
	/**
	 * This implements the porter stemmer algorithm for finding a stem given a word
	 * @param input string
	 * @return stemmed version of the string
	 */
	public String stem(String s) {
		return s;
	}
}