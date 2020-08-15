/**
 * 
 */
package com.example.grpc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.grpc.GreetingServiceOuterClass.HelloRequest;
import com.example.grpc.GreetingServiceOuterClass.HelloResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;

/**
 * Class represents bidirectional streaming RPC model.
 * @author Ashish Tulsankar
 *
 */
public class BidirStreamClient {
	private static Logger log = LogManager.getLogger(BidirStreamClient.class);

	public static void main( String[] args ) throws Exception
	{
		log.info("*** Bidirectional Streaming RPC ***");
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

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
								log.info("Sending~ {}", name);
								HelloRequest request = HelloRequest.newBuilder().setName(name).build();
								requestStream.onNext(request);
							} else {
								log.info("Data sent successfully, No further requests shall be made !");
								requestStream.onCompleted();
							}
						}
					}
				});
			}

			@Override
			public void onNext(HelloResponse value) {
				log.info("Received~ {}",value.getGreeting());
				// Signal the sender to send one message as well
				requestStream.request(1);
			}

			@Override
			public void onError(Throwable t) {
				log.trace("onError~ {}",t);
				done.countDown();
			}

			@Override
			public void onCompleted() {


				log.info("Data received successfully, No further requests shall be received !");
				done.countDown();

			}
		};

		//Note: clientResponseObserver is handling both request and response stream processing.
		stub.bidirGreets(clientResponseObserver);
		done.await();
		channel.shutdown();
		channel.awaitTermination(30, TimeUnit.SECONDS);

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
