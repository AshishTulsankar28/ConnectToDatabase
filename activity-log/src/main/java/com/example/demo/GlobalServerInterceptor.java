package com.example.demo;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;
/**
 * To represent {@link ServerInterceptor}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Configuration
public class GlobalServerInterceptor {
	
	@Bean
	public GlobalServerInterceptorConfigurer globalServerInterceptorConfigurer() {
		return new GlobalServerInterceptorConfigurer() {
			
			@Override
			public void configureServerInterceptors(List<ServerInterceptor> interceptors) {
				interceptors.add(new LoggerInterceptor());
			}
		};
		
	}

}
