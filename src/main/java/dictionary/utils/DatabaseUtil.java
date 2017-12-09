package dictionary.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author Alex Killian
 *
 * Utility class for our database.
 * Inspired by HibernateUtil.java from Hibernate slides on class website by Elizabeth Boese.
 */
public class DatabaseUtil {

	/**
	 * The name of the dictionary database.
	 */
	public static final String DB_NAME = "dictionarydb";
	
	/**
	 * The host of the DB connection.
	 */
	public static final String HOST = "localhost";
	
	/**
	 * The port of the DB connection.
	 */
	public static final int PORT = 3306;
	
	/**
	 * If the DB is auto created or not.
	 */
	public static final boolean AUTO_CREATE_DB = true;
	
	/**
	 * The name of the hibernate config file.
	 */
	public static final String HIBERNATE_CFG_NAME = "hibernate.cfg.xml";
	
	/**
	 * The Dev username for the DB.
	 */
	public static final String USERNAME = "root";
	
	/**
	 * The Dev password for the DB.
	 */
	public static final String PASSWORD = "root";
	
	/**
	 * The session factory for the while application.
	 */
	private static SessionFactory sessionFactory = null;
	
	/**
	 * Constructs and returns the JDBC connection string.
	 * 
	 * @return String
	 */
	public static String getConnectionString() {
		return "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?createDatabaseIfNotExist=" + AUTO_CREATE_DB;
	}

	/**
	 * Returns the application's session factory.
	 * 
	 * @return SessionFactory
	 */
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration config = new Configuration();
				config.setProperty("hibernate.connection.url", getConnectionString());
				config.setProperty("hibernate.connection.username", USERNAME);
				config.setProperty("hibernate.connection.password", PASSWORD);
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
