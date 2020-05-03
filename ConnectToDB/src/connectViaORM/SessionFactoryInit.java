/**
 * 
 */
package connectViaORM;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author Ashish Tulsankar
 *
 */
public class SessionFactoryInit {
	private static final SessionFactory SESSION_FACTORY;

	/**
	 * Initialize the SessionFactory instance.
	 */
	static {
		// Create a Configuration object.
		Configuration config = new Configuration();
		// Configure using the application resource named hibernate.cfg.xml.
		config.configure("/resources/hibernate.cfg.xml");
		config.addAnnotatedClass(Student.class);
		// Extract the properties from the configuration file.
		Properties prop = config.getProperties();

		// Create StandardServiceRegistryBuilder using the properties.
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(prop);

		// Build a ServiceRegistry
		ServiceRegistry registry = builder.build();

		// Create the SessionFactory using the ServiceRegistry
		SESSION_FACTORY = config.buildSessionFactory(registry);
	}
	
	public static SessionFactory getSession() {
		return SESSION_FACTORY;
		
	}
}
