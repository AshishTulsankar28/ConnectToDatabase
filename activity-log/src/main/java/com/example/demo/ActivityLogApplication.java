package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * Application bootstrap
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.example.demo"})
public class ActivityLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityLogApplication.class, args);
	}

}
