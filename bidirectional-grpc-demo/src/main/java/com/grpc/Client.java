package com.grpc;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * gRPC client
 * @author Ashish Tulsankar
 *
 */
public class Client {
	public static void main(String [] args) throws IOException, InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
		CallServiceGrpc.CallServiceStub service = CallServiceGrpc.newStub(channel);
		AtomicReference<StreamObserver<BiDirectionalCallService.RequestCall>> requestObserverRef = new AtomicReference<>();
		CountDownLatch finishedLatch = new CountDownLatch(1);
		
		StreamObserver<BiDirectionalCallService.RequestCall> observer = service.connect(new StreamObserver<BiDirectionalCallService.ResponseCall>() {
			@Override
			public void onNext(BiDirectionalCallService.ResponseCall value) {
				//TODO will define way to terminating condition
				System.out.println("onNext in client "+value.getResp());
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				//TODO checking with request parameter to be sent on onNext callback
				BiDirectionalCallService.RequestCall request = BiDirectionalCallService.RequestCall.newBuilder().setReq("Ping").build();
				//RequestCall request=BiDirectionalCallService.RequestCall.getDefaultInstance();
				requestObserverRef.get().onNext(request);
				
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("on error");
				t.printStackTrace();
			}

			@Override
			public void onCompleted() {
				System.out.println("on completed");
				finishedLatch.countDown();
			}
		});
		requestObserverRef.set(observer);
		observer.onNext(BiDirectionalCallService.RequestCall.getDefaultInstance());
		finishedLatch.await();
		observer.onCompleted();
	}
}
