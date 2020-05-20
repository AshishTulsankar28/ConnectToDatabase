package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInit implements WebApplicationInitializer{
	
	Logger logger=LogManager.getLogger();

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		logger.info("WEBSERVER - Initializing Annotation based appContext ");
		AnnotationConfigWebApplicationContext appContext=new AnnotationConfigWebApplicationContext();
		appContext.register(CustomConfig.class);
		appContext.setServletContext(servletContext);
		
		logger.info("WEBSERVER - Registering Dispatcher servlet ");
		ServletRegistration.Dynamic servlet=servletContext.addServlet("springDispatcherServlet", new DispatcherServlet(appContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
	}

}
