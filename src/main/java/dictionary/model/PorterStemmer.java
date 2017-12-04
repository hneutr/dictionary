package dictionary.model;

import org.tartarus.martin.*;

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
		Stemmer stemmer = new Stemmer();
		// Add each char in the string to the stemmer
		for (int i = 0; i < s.length(); i++) {
	        stemmer.add(s.charAt(i));
	    }
		
		stemmer.stem();
		
		return stemmer.toString();
	}
}