package dictionary.driver;

import dictionary.model.IStemmer;
import dictionary.model.PorterStemmer;

public class StemmerTestDriver {
	public static void main(String[] args) {
		System.out.println("TEST");
		IStemmer stemmer = new PorterStemmer();
		System.out.println(stemmer.stem("running"));
	}
}
