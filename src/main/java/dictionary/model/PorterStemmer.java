package dictionary.model;

//import edu.stanford.nlp.process.Stemmer;

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
		//Stemmer stemmer = new Stemmer();
		return s; //stemmer.stem(s);
	}
}