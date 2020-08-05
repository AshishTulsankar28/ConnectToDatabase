package com.example.grpc;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

public class Client
{
	public static void main( String[] args ) throws Exception
	{
		// Channel is the abstraction to connect to a service endpoint
		// Let's use plaintext communication because we don't have certs
		final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();
		usingBlockingStub(channel);
		usingAsyncStub(channel);

		//A Channel should be shutdown before stopping the process.
		//channel.shutdownNow();
	}

	private static void usingAsyncStub(final ManagedChannel channel) {
		System.out.println("**********usingAsyncStub*********");
		GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

		// Construct a request
		GreetingServiceOuterClass.HelloRequest request =
				GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Ashish Tulsankar")
				.build();

		stub.responsiveGreets(request, new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {
			public void onNext(GreetingServiceOuterClass.HelloResponse response) {
				System.out.println("responsiveGreets:\n"+response);
			}
			public void onError(Throwable t) {
				System.out.println("Error "+t.getMessage());
			}
			public void onCompleted() {
				System.out.println("Done with response as stream");
				// Typically you'll shutdown the channel somewhere else.
				// But for the purpose of the lab, we are only making a single
				// request. We'll shutdown as soon as this request is done.
				channel.shutdownNow();
			}
		});
	}

	private static void usingBlockingStub(final ManagedChannel channel) {
		System.out.println("**********usingBlockingStub*********");
		// It is up to the client to determine whether to block the call
		// Here we create a blocking stub, but an async stub,
		// or an async stub with Future are always possible.
		GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);

		GreetingServiceOuterClass.HelloRequest request =GreetingServiceOuterClass.HelloRequest.newBuilder()
				.setName("Abhishek")
				.build();

		// Finally, make the call using the stub
		GreetingServiceOuterClass.HelloResponse response = 
				stub.greeting(request);

		System.out.println("Response from blocking stub:\n"+response);
	}
}