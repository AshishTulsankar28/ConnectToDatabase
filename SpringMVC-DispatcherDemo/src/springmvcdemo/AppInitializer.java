/**
 * 
 */
package springmvcdemo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Entry point of the application to configure basic properties of dispatcher servlet.
 * This example uses Annotation based configuration
 * @author Ashish Tulsankar
 *
 */
public class AppInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/**
		 * Configure servletContext Programmatically
		 */
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);
		context.setServletContext(servletContext);

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
	}

}
