package dictionary.driver;

import org.hibernate.Session;
import dictionary.model.*;
import dictionary.utils.DatabaseUtil;

public class DictionaryTestDriver {

	public static void main(String[] args) {
		System.out.println("TEST");
		
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// test word form
		WordForm wf = new WordForm("test");
		session.save(wf);
		
		// test word sense
		WordSense ws = new WordSense(new Definition("test"), new PartOfSpeech("Noun"));
		session.save(ws);
		
		// test entry
		DictionaryEntry e = new DictionaryEntry("word", wf);
		session.save(e);
		
		session.getTransaction().commit();
		session.close();
	}
}
