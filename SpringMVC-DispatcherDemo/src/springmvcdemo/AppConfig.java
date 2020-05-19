package springmvcdemo;

import java.util.List;
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
@ComponentScan
public class AppConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public UrlBasedViewResolver viewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}


	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		ObjectMapper mapper = new ObjectMapper();


		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) converter;
				m.setObjectMapper(mapper);
			}
		} 
	}
}
