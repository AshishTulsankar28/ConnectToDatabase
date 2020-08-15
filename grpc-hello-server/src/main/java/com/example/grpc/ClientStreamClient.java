/**
 * 
 */
package com.example.grpc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.grpc.GreetingServiceOuterClass.HelloRequest;
import com.example.grpc.GreetingServiceOuterClass.HelloResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Class represents client side streaming RPC model.
 * @author Ashish Tulsankar
 *
 */
public class ClientStreamClient {
	private static Logger log = LogManager.getLogger(ClientStreamClient.class);

	public static void main( String[] args ) throws Exception
	{
		log.info("*** Client Side Streaming RPC ***");
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		final CountDownLatch finishLatch = new CountDownLatch(1);
		StreamObserver<HelloResponse> responseObserver=new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {

			@Override
			public void onNext(HelloResponse value) {
				log.info("Received~ {} ",value.getGreeting());	
			}

			@Override
			public void onError(Throwable t) {
				log.trace("onError~ {}",t);
				finishLatch.countDown();
			}

			@Override
			public void onCompleted() {				
				try {
					finishLatch.countDown();
					channel.shutdown();
					channel.awaitTermination(30, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					log.info("InterruptedException~ {}",e);
				}
			}
		};

		StreamObserver<HelloRequest> requestObserver = stub.requestingGreets(responseObserver);
		try {
			
			
			for (int i = 0; i < 5; ++i) {

				
				GreetingServiceOuterClass.HelloRequest request =
						GreetingServiceOuterClass.HelloRequest.newBuilder()
						.setName(i+" Request ")
						.build();

				log.info("Sending~ {}",request.getName());
				requestObserver.onNext(request);
				// Sleep for a bit before sending the next one.
				Thread.sleep(100);

				if (finishLatch.getCount() == 0) {
					// RPC completed or errored before we finished sending.
					// Sending further requests won't error, but they will just be thrown away.
					return;
				}
			}
		} catch (RuntimeException e) {
			// Cancel RPC
			requestObserver.onError(e);
			throw e;
		}
		// Mark the end of requests
		requestObserver.onCompleted();
	}
}
