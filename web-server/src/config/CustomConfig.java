package config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan({"config","services","controllers"})
public class CustomConfig implements WebMvcConfigurer{
	Logger logger = Logger.getLogger(CustomConfig.class);
	
	public void addViewControllers(ViewControllerRegistry viewCtrlRegistry) {
		logger.info("WEBSERVER - addViewControllers");
		viewCtrlRegistry.addViewController("/").setViewName("home");
	}
	
	@Bean
	public UrlBasedViewResolver viewResolver() {
		logger.info("WEBSERVER - UrlBasedViewResolver");
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.info("WEBSERVER - CustomConfig added");
		ObjectMapper mapper = new ObjectMapper();

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) converter;
				m.setObjectMapper(mapper);
			}
		} 
	}

}
