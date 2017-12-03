package dictionary.driver;

import org.hibernate.Session;

import dictionary.controller.Dictionary;
import dictionary.model.*;
import dictionary.utils.DatabaseUtil;

public class DictionaryTestDriver {
	
	private static DictionaryEntry getTestEntry() {
		
		// test word form
		WordForm wf = new WordForm("test");
		
		// test word sense
		WordSense ws = new WordSense(new Definition("test"), new PartOfSpeech("Noun"));
		ws.addWordForm(wf);
		
		// test entry
		DictionaryEntry e = new DictionaryEntry("word", wf);
		e.addSense(ws);
		return e;
	}

	public static void main(String[] args) {
		System.out.println("TEST");
		
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		DictionaryEntry e = getTestEntry();
		Dictionary.getInstance().addEntry(e);
		
		session.getTransaction().commit();
		session.close();
	}
}
