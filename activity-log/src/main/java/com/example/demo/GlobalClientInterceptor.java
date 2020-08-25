/**
 * 
 */
package com.example.demo;

import org.springframework.context.annotation.Configuration;

import io.grpc.ClientInterceptor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

/**
 * To represent {@link ClientInterceptor}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Configuration
public class GlobalClientInterceptor {
	
	@GrpcGlobalClientInterceptor
    ClientInterceptor logClientInterceptor() {
        return new LoggerInterceptor();
    }
	
}
