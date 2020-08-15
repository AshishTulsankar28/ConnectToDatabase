/**
 * 
 */
package com.example.grpc;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Class represents basic streaming RPC model.
 * @author Ashish Tulsankar
 *
 */
public class BasicStreamClient {
	private static Logger log = LogManager.getLogger(BasicStreamClient.class);

	public static void main( String[] args ) throws Exception
	{
		// Channel is the abstraction to connect to a service end-point using the Netty transport
		// TODO we can configure channel using zookeeper URI. Also see ManagedChannelBuilder.forAddress("address","port");
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();
		log.info("*** Basic Streaming RPC ***");

		GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);

		GreetingServiceOuterClass.HelloRequest request =GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Ashish")
				.build();
		log.info("Sending~ {} ",request.getName());
		// Invoke method on server & get response
		GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);

		log.info("Received~ {} ",response);
		
		channel.shutdown();
		channel.awaitTermination(30, TimeUnit.SECONDS);
	}
}
