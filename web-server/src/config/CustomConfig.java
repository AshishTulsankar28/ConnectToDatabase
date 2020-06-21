package config;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import constants.DB_CONSTANTS;


@Configuration
@EnableWebMvc
@ComponentScan({"config","services","controllers"})
@EnableJpaRepositories(basePackages = {"repositories"})
@EnableTransactionManagement
public class CustomConfig implements WebMvcConfigurer{
	Logger logger=LogManager.getLogger();

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



	/*Configured Hikari DS*/
	@Bean
	public HikariDataSource hikariDS() {

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(DB_CONSTANTS.DB_DRIVER);
		config.setJdbcUrl(DB_CONSTANTS.DB_URL);
		config.setUsername(DB_CONSTANTS.DB_USER);
		config.setPassword(DB_CONSTANTS.DB_PWD);
		config.setMaximumPoolSize(Integer.valueOf(DB_CONSTANTS.MAX_POOL_SIZE));
		config.setIdleTimeout(Integer.valueOf(DB_CONSTANTS.IDLE_TIMEOUT));
		config.setConnectionTimeout(Integer.valueOf(DB_CONSTANTS.CONNECTION_TIMEOUT));
		config.setMinimumIdle(Integer.valueOf(DB_CONSTANTS.MIN_IDLE));
		config.setPoolName("hibernate_hikari_pool");


		HikariDataSource hikariDS=new HikariDataSource(config);

		return hikariDS;
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
		factory.setDataSource(hikariDS());
		factory.afterPropertiesSet();
		//factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory.getObject();
	}

	/**
	 * Template based approach for Data Access
	 * @return HibernateTemplate
	 * @see
	 * {@link https://nofluffjuststuff.com/blog/shay_banon/2006/08/hibernate_vs_spring__hibernate_template}
	 * {@link https://spring.io/blog/2007/06/26/so-should-you-still-use-spring-s-hibernatetemplate-and-or-jpatemplate}
	 * <dt>Last Modified:</dt>
	 * <dd> 20 June,2020 </dd>
	 */
	//	@Bean
	//	public HibernateTemplate hibernateTemplate() {
	//		return new HibernateTemplate(HibernateUtil.getSessionFactory());
	//
	//	}

	/**
	 * Implementation of required datasource per database connection
	 * using jdbc.datasource.DriverManagerDataSource
	 * 
	 * @return DataSource
	 * <dt>Last Modified:</dt>
	 * <dd> 20 June,2020 </dd>
	 * Code Commented since Hikari connection pooling is implemented
	 */
	//		@Bean
	//		public DataSource dataSource() {
	//			DriverManagerDataSource dataSource = new DriverManagerDataSource();
	//	
	//			dataSource.setDriverClassName(DbProperties.DB_DRIVER);
	//			dataSource.setUrl(DbProperties.DB_URL);
	//			dataSource.setUsername(DbProperties.DB_USER);
	//			dataSource.setPassword(DbProperties.DB_PWD);
	//	
	//			return dataSource;
	//		}
}
