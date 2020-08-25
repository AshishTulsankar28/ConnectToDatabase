package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
/**
 * Intercept server & client calls before they're dispatched
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
public class LoggerInterceptor implements ServerInterceptor,ClientInterceptor {

   private static Logger log=LogManager.getLogger(LoggerInterceptor.class);	
	
	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		log.info("SERVER INTERCEPT~ METHOD: {} HEADERS: {}",call.getMethodDescriptor().getFullMethodName(),headers);
		return next.startCall(call, headers);
	}

	@Override
	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
			CallOptions callOptions, Channel next) {
		log.info("CLIENT INTERCEPT~ METHOD: {} OPTIONS: {} HOST_DETAILS: {}",method,callOptions,next.authority());
		return next.newCall(method, callOptions);
	}

}
