package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInit implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		
		AnnotationConfigWebApplicationContext appContext=new AnnotationConfigWebApplicationContext();
		appContext.register(CustomConfig.class);
		appContext.setServletContext(servletContext);
		
		ServletRegistration.Dynamic servlet=servletContext.addServlet("TestingServlet", new DispatcherServlet(appContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
	}

}
