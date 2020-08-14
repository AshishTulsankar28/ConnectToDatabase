/**
 * 
 */
package com.grpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import com.google.common.util.concurrent.MoreExecutors;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

/**
 * gRPC server
 * @author Ashish Tulsankar
 *
 */
public class App {

	private static Executor executor;

	public static void main(String[] args) throws IOException, InterruptedException {
		NettyServerBuilder builder = NettyServerBuilder.forAddress(new InetSocketAddress("localhost", 8080));
		executor = MoreExecutors.directExecutor();
		builder.executor(executor);
		Server server = builder.addService(new CallServiceGrpcImpl()).build();

		server.start();

		System.out.println("Server has started");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.shutdown();
		}));

		server.awaitTermination();
	}

}
