/**
 * 
 */
package com.example.demo;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.model.DB_CONSTANTS;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Class to configure {@link EntityManagerFactory}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class CustomConfig  {


	@Bean
	public HikariDataSource hikariDS() {

		HikariConfig config = new HikariConfig();

		//		Configure MySQL
		//		config.setDriverClassName(DB_CONSTANTS.MYSQL_DRIVER);
		//		config.setJdbcUrl(DB_CONSTANTS.MYSQL_URL);
		//		config.setUsername(DB_CONSTANTS.MYSQL_USER);
		//		config.setPassword(DB_CONSTANTS.MYSQL_PWD);


		// Configure H2 
		config.setDriverClassName(DB_CONSTANTS.H2_DRIVER);
		config.setJdbcUrl(DB_CONSTANTS.H2_URL);
		config.setUsername(DB_CONSTANTS.H2_USER);
		config.setPassword(DB_CONSTANTS.H2_PWD);

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
		vendorAdapter.setDatabase(Database.H2); //Database.MYSQL

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.example.demo.model");
		factory.setDataSource(hikariDS());
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
