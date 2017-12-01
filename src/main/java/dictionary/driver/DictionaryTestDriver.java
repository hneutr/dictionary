package dictionary.driver;

import org.hibernate.Session;
import dictionary.model.DictionaryEntry;
import dictionary.model.WordForm;
import dictionary.utils.DatabaseUtil;

public class DictionaryTestDriver {

	public static void main(String[] args) {
		System.out.println("TEST");
		
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		WordForm wf = new WordForm("test");
		session.save(wf);
		DictionaryEntry e = new DictionaryEntry("word", wf);
		session.save(e);
		
		session.getTransaction().commit();
		session.close();
	}
}
