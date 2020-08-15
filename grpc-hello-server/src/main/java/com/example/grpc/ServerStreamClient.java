/**
 * 
 */
package com.example.grpc;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Class represents server side streaming RPC model.
 * @author Ashish Tulsankar
 *
 */
public class ServerStreamClient {
	private static Logger log = LogManager.getLogger(ServerStreamClient.class);

	public static void main( String[] args ) throws Exception
	{
		log.info("*** Server Side Streaming RPC ***");
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		GreetingServiceOuterClass.HelloRequest request =
				GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Saurabh")
				.build();

		log.info("Sending~ {} ",request.getName());
		stub.responsiveGreets(request, new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {
			
			public void onNext(GreetingServiceOuterClass.HelloResponse response) {
				log.info("Received~ {} ",response);
			}
			public void onError(Throwable t) {
				log.trace("OnError~ {} ",t);
			}
			public void onCompleted() {

				try {
					log.info("Successful stream completion !");
					channel.shutdown();
					channel.awaitTermination(30, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					log.trace("InterruptedException~ {} ",e);
				}
			}
		});
	}
}
