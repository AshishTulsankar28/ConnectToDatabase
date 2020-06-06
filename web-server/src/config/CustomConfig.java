package config;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
@EnableWebMvc
@ComponentScan({"config","services","controllers","views"})
@EnableJpaRepositories(basePackages = {"repositories"})
@EnableTransactionManagement
public class CustomConfig implements WebMvcConfigurer{
	//Logger logger=LogManager.getLogger();

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/ashish";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "root";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "root";


	/* Configure the view that will be displayed on start-up */
	public void addViewControllers(ViewControllerRegistry viewCtrlRegistry) {
		//logger.trace("WEBSERVER - addViewControllers");
		viewCtrlRegistry.addViewController("/").setViewName("home");
	}

	@Bean
	public UrlBasedViewResolver viewResolver() {
		//logger.trace("WEBSERVER - UrlBasedViewResolver");
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	/* Map incoming request data to view model using jackson*/
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//logger.trace("WEBSERVER - CustomConfig added");
		ObjectMapper mapper = new ObjectMapper();
		/*To map a LocalDate into a String like 1982-06-23*/
		mapper.registerModule(new JavaTimeModule());
		/*represent a Date as a String in JSON*/
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) converter;
				m.setObjectMapper(mapper);
			}
		} 
	}

	/*configure CORS (cross origin resource sharing) filter*/
	public void addCorsMappings(CorsRegistry registry) {
		//logger.trace("WEBSERVER - addCorsMappings called");
		registry.addMapping("/**");

		//Uncomment below code to check the working
		/*
		 * .allowedOrigins("https://www.test-cors.org") .allowedMethods("GET")
		 * .allowCredentials(true).maxAge(3600); ...Similarly you can configure more
		 * filters
		 */

	}

	/* pre & post processing of controller method invocations and resource handler requests*/
	public void addInterceptors(InterceptorRegistry registry) {
		//logger.trace("WEBSERVER - addInterceptors called");
		registry.addInterceptor(new CustomLoggerInterceptor());
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
		dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
		dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
		dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		
		EntityManagerFactory factory = entityManagerFactory();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);
		vendorAdapter.setDatabase(Database.MYSQL);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("views");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		//factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory.getObject();
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(HibernateUtil.getSessionFactory());
		
	}
}
