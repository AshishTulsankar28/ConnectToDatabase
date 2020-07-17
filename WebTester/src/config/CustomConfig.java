package config;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


	/* Map incoming request data to view model using jackson*/
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//logger.trace("WEBSERVER - CustomConfig added");
		ObjectMapper mapper = new ObjectMapper();

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) converter;
				m.setObjectMapper(mapper);
			}
		} 
	}

	
}
