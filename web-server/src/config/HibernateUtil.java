package config;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import constants.DB_CONSTANTS;
import views.Employees;

/**
 * Class used for defining Hibernate configuration properties & build {@link SessionFactory}
 * @author Ashish Tulsankar
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	/**
	 * Method to set hibernate properties programmatically.
	 * @return {@link SessionFactory} object with defined properties
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties props = new Properties();
				props.put(Environment.DRIVER, DB_CONSTANTS.DB_DRIVER);
				props.put(Environment.URL, DB_CONSTANTS.DB_URL);
				props.put(Environment.USER, DB_CONSTANTS.DB_USER);
				props.put(Environment.PASS, DB_CONSTANTS.DB_PWD);
				props.put(Environment.DIALECT, DB_CONSTANTS.DB_DIALECT);
				props.put(Environment.SHOW_SQL, DB_CONSTANTS.SHOW_SQL);
				props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, DB_CONSTANTS.SESSION_CTX_SCOPE);
				//settings.put(Environment.HBM2DDL_AUTO, "create-drop");	
				/* HikariCP settings*/
				props.put("hibernate.hikari.connectionTimeout", DB_CONSTANTS.CONNECTION_TIMEOUT);
				props.put("hibernate.hikari.minimumIdle", DB_CONSTANTS.MIN_IDLE);
				props.put("hibernate.hikari.maximumPoolSize", DB_CONSTANTS.MAX_POOL_SIZE);
				props.put("hibernate.hikari.idleTimeout", DB_CONSTANTS.IDLE_TIMEOUT);
				props.put(Environment.CONNECTION_PROVIDER, DB_CONSTANTS.CONNECTION_PROVIDER);

				configuration.setProperties(props);
				configuration.addAnnotatedClass(Employees.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}


}