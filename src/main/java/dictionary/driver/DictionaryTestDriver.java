package dictionary.driver;

import org.hibernate.Session;
import dictionary.model.DictionaryEntry;
import dictionary.utils.DatabaseUtil;

public class DictionaryTestDriver {

	public static void main(String[] args) {
		System.out.println("TEST");
		
		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		DictionaryEntry e = new DictionaryEntry();
		session.save(e);
		
		session.getTransaction().commit();
		session.close();
	}
}
