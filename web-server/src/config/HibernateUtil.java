package config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import constants.DB_CONSTANTS;
import views.Employees;

/**
 * @author Ashish Tulsankar
 * <br>
 * Class used for defining Hibernate configuration properties & build {@link SessionFactory}.
 * 
 * You can either create {@link SessionFactory} using native hibernate implementation or we can use
 * {@link EntityManagerFactory} which is synonymous.
 * <br>So, changing {@link SessionFactory} to {@link EntityManagerFactory} 
 * <dt>Last Modified:</dt>
 * <dd>20 June,2020</dd>
 * @deprecated
 * 
 * @see
 * https://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html_single/
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
				//DB related properties
				Properties props = new Properties();
				props.put(Environment.DRIVER, DB_CONSTANTS.DB_DRIVER);
				props.put(Environment.URL, DB_CONSTANTS.DB_URL);
				props.put(Environment.USER, DB_CONSTANTS.DB_USER);
				props.put(Environment.PASS, DB_CONSTANTS.DB_PWD);
				props.put(Environment.DIALECT, DB_CONSTANTS.DB_DIALECT);		
				props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, DB_CONSTANTS.SESSION_CTX_SCOPE);
				props.put(Environment.SHOW_SQL, DB_CONSTANTS.SHOW_SQL);
				//settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				
				
				// HikariCP configuration settings
				props.put("hibernate.hikari.connectionTimeout", DB_CONSTANTS.CONNECTION_TIMEOUT);
				props.put("hibernate.hikari.minimumIdle", DB_CONSTANTS.MIN_IDLE);
				props.put("hibernate.hikari.maximumPoolSize", DB_CONSTANTS.MAX_POOL_SIZE);
				props.put("hibernate.hikari.idleTimeout", DB_CONSTANTS.IDLE_TIMEOUT);
				props.put("hibernate.hikari.poolName", "hibernate_hikari_pool1");
				props.put(Environment.CONNECTION_PROVIDER, DB_CONSTANTS.CONNECTION_PROVIDER);

				configuration.setProperties(props);
				

				configuration.buildSessionFactory();
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