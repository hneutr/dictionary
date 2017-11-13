package dictionary.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author Alex Killian
 *
 * Utility class for our database.
 * Inspired by HibernateUtil.java from Hibernate slides on class website by Boese.
 */
public class DatabaseUtil {

	/*
	 * The name of the dictionary database.
	 */
	public static final String DB_NAME = "dictionarydb";
	
	/**
	 * The name of the hibernate config file.
	 */
	public static final String HIBERNATE_CFG_NAME = "hibernate.cfg.xml";
	
	/**
	 * The session factory for the while application.
	 */
	private static SessionFactory sessionFactory = null;

	/**
	 * Returns the application's session factory.
	 * 
	 * @return SessionFactory
	 */
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration config = new Configuration();
				config.configure(HIBERNATE_CFG_NAME);
				StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
				sessionFactory = config.buildSessionFactory(serviceRegistry);
			}
			catch (Exception e) {
				System.err.print("Failed to create SessionFactory object!");
			}
		}
		return sessionFactory;
	}
}
