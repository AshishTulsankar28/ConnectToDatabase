package config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan({"config","services","controllers"})
public class CustomConfig implements WebMvcConfigurer{
	Logger logger=LogManager.getLogger();

	public void addViewControllers(ViewControllerRegistry viewCtrlRegistry) {
		logger.trace("WEBSERVER - addViewControllers");
		viewCtrlRegistry.addViewController("/").setViewName("home");
	}

	@Bean
	public UrlBasedViewResolver viewResolver() {
		logger.trace("WEBSERVER - UrlBasedViewResolver");
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	// Map incoming request data to view.
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.trace("WEBSERVER - CustomConfig added");
		ObjectMapper mapper = new ObjectMapper();

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) converter;
				m.setObjectMapper(mapper);
			}
		} 
	}

	//CORS filter
	public void addCorsMappings(CorsRegistry registry) {
		logger.trace("WEBSERVER - addCorsMappings called");
		registry.addMapping("/**");

		/*Uncomment below code to check the working*/
		//		.allowedOrigins("https://www.test-cors.org")
		//		.allowedMethods("GET")
		//		.allowCredentials(true).maxAge(3600);

		// ...Similarly you can configure more filters

	}

	public void addInterceptors(InterceptorRegistry registry) {
		logger.trace("WEBSERVER - addInterceptors called");
		registry.addInterceptor(new CustomLoggerInterceptor());
	}

}
