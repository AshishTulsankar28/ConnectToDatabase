package com.example.grpc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.grpc.GreetingServiceOuterClass.HelloRequest;
import com.example.grpc.GreetingServiceOuterClass.HelloResponse;

import io.grpc.*;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;

/**
 * Class represents gRPC client
 * @author Ashish Tulsankar
 *
 */
public class Client
{
	private static Logger log = LogManager.getLogger(Client.class);

	public static void main( String[] args ) throws Exception
	{
		// Channel is the abstraction to connect to a service end-point using the Netty transport
		// TODO we can configure channel using zookeeper URI. Also see ManagedChannelBuilder.forAddress("address","port");
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();

		/**
		 * TODO We can use different streaming methods as required
		 * As of now, we will call different methods on completion of current method & at last will close the channel.
		 * Sequence of methods - 1. Basic 2. Server side 3. Client side 4. Bidirectional streaming
		 */
		usingBlockingStub(channel);
		//asyncServerStream(channel);
		//asyncClientStream(channel);
		//asyncBidirectStream(channel);

		// TODO check for the graceful shutdown of the channel
		//channel.shutdown();
		//channel.awaitTermination(2, TimeUnit.MINUTES);
	}




	/**
	 * Client side streaming rpc
	 * @param channel
	 */
	private static void asyncClientStream(ManagedChannel channel) throws InterruptedException {
		log.info("*** Async stub Implementation : Client Side Streaming RPC ***");
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		final CountDownLatch finishLatch = new CountDownLatch(1);
		StreamObserver<HelloResponse> responseObserver=new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {

			@Override
			public void onNext(HelloResponse value) {
				// TODO Implement OnNext Callback, will receive single response here
				log.info("server response- "+value.getGreeting());	
			}

			@Override
			public void onError(Throwable t) {

				t.printStackTrace();
				finishLatch.countDown();
			}

			@Override
			public void onCompleted() {
				// TODO shutdown channel
				finishLatch.countDown();
				// calling bidirectional streaming
				try {
					asyncBidirectStream(channel);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		StreamObserver<HelloRequest> requestObserver = stub.requestingGreets(responseObserver);
		try {
			// Sending multiple requests 
			log.info("Sending 5 requests from client");
			for (int i = 0; i < 5; ++i) {

				GreetingServiceOuterClass.HelloRequest request =
						GreetingServiceOuterClass.HelloRequest.newBuilder()
						.setName(i+" Request ")
						.build();

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

	/**
	 * Bidirectional streaming rpc
	 * @param channel
	 * @throws InterruptedException
	 */
	private static void asyncBidirectStream(ManagedChannel channel) throws InterruptedException {
		log.info("*** Async stub Implementation : Bidirectional Streaming RPC ***");
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		//For sync behavior
		final CountDownLatch done = new CountDownLatch(1);

		ClientResponseObserver<HelloRequest, HelloResponse> clientResponseObserver = new ClientResponseObserver<HelloRequest, HelloResponse>() {

			ClientCallStreamObserver<HelloRequest> requestStream;

			@Override
			public void beforeStart(final ClientCallStreamObserver<HelloRequest> requestStream) {
				this.requestStream = requestStream;

				requestStream.disableAutoRequestWithInitial(1);

				requestStream.setOnReadyHandler(new Runnable() {
					// An iterator is used so we can pause and resume iteration of the request data.
					Iterator<String> iterator = names().iterator();

					@Override
					public void run() {
						// Start generating values from where we left off on a non-gRPC thread.
						while (requestStream.isReady()) {
							if (iterator.hasNext()) {
								// Send more messages if there are more messages to send.
								String name = iterator.next();
								log.info("Request Data to send --> " + name);
								HelloRequest request = HelloRequest.newBuilder().setName(name).build();
								requestStream.onNext(request);
							} else {
								// Signal completion if there is nothing left to send.
								requestStream.onCompleted();
							}
						}
					}
				});
			}

			@Override
			public void onNext(HelloResponse value) {
				log.info(value.getGreeting()+" <-- Received Response");
				// Signal the sender to send one message as well
				requestStream.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				done.countDown();
			}

			@Override
			public void onCompleted() {
				log.info("Streamimg completed from client end");
				done.countDown();
			}
		};

		//Note: clientResponseObserver is handling both request and response stream processing.
		stub.bidirGreets(clientResponseObserver);
		done.await();

		//TODO shutdown channel
		channel.shutdown();
	}

	/**
	 * server side streaming using asynchronous stub
	 * @param channel
	 */
	private static void asyncServerStream(final ManagedChannel channel) {
		log.info("*** Async stub Implementation : Server Side Streaming RPC ***");
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		GreetingServiceOuterClass.HelloRequest request =
				GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Saurabh")
				.build();

		// Invoke method
		stub.responsiveGreets(request, new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {
			public void onNext(GreetingServiceOuterClass.HelloResponse response) {
				log.info("Server side streaming- Method from server invoked | Response- "+response);
			}
			public void onError(Throwable t) {
				t.printStackTrace();
			}
			public void onCompleted() {
				log.info("successful stream completion !");
				//TODO shutdown channel
				
				// Calling client side streaming
				try {
					asyncClientStream(channel);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Represents typical request response.<br>
	 * Uses simple RPC call using blocking stub.<br>
	 * Blocking stub is synchronous, waits for server to respond & will either return response or an exception.
	 * @param channel 
	 */
	private static void usingBlockingStub(final ManagedChannel channel) {
		log.info("*** Blocking stub Implementation : Basic RPC call ***");

		// Stub : Piece of code to invoke method on server
		GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);

		GreetingServiceOuterClass.HelloRequest request =GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Ashish")
				.build();

		// Finally, make the call using the stub
		GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);

		log.info("gRPC server response: "+response);
		asyncServerStream(channel);

	}

	/**
	 * Sample Request data for streaming
	 * 
	 * @return {@link List} of {@link String}
	 */
	private static List<String> names() {
		return Arrays.asList(
				"Saurabh",
				"Todd",
				"Abhishek",
				"Christiano",
				"Ashish",
				"Sachin",
				"Hamid",
				"Sophia",
				"Jackson",
				"Emma",
				"Aiden",
				"Olivia",
				"Lucas",
				"Ava",
				"Liam",
				"Mia",
				"Noah",
				"Isabella",
				"Ethan",
				"Riley",
				"Mason",
				"Aria",
				"Caden",
				"Zoe",
				"Oliver",
				"Charlotte",
				"Elijah",
				"Lily"
				);
	}
}