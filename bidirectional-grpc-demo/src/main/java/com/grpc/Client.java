package com.grpc;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * gRPC client
 * @author Ashish Tulsankar
 *
 */
public class Client {
	private static Logger log = LogManager.getLogger(Client.class);

	public static void main(String [] args) throws IOException, InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
		CallServiceGrpc.CallServiceStub asyncStub = CallServiceGrpc.newStub(channel);
		AtomicReference<StreamObserver<BiDirectionalCallService.RequestCall>> requestObserverRef = new AtomicReference<>();
		CountDownLatch finishedLatch = new CountDownLatch(1);

		StreamObserver<BiDirectionalCallService.RequestCall> observer = asyncStub.connect(new StreamObserver<BiDirectionalCallService.ResponseCall>() {
			
			int counter=0;

			@Override
			public void onNext(BiDirectionalCallService.ResponseCall value) {
		
				log.info("CLIENT: Received {} ",value.getResp());

				//TODO Determine termination condition. We are manually limiting it to 7 calls for demo
				if(counter>=7) {
					log.info("CLIENT: Terminating further requests ");
					this.onCompleted();
				}
				
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				BiDirectionalCallService.RequestCall request = BiDirectionalCallService.RequestCall.newBuilder().setReq("Ping "+counter).build();
				log.info("Sending {} to server",request.getReq());
				requestObserverRef.get().onNext(request);


				counter++;



			}

			@Override
			public void onError(Throwable t) {
				log.trace("CLIENT: on error {}",t);
			}

			@Override
			public void onCompleted() {
				
				try {
					log.info("CLIENT: on completed");
					finishedLatch.countDown();
					channel.shutdown();
					channel.awaitTermination(30, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		requestObserverRef.set(observer);
		observer.onNext(BiDirectionalCallService.RequestCall.newBuilder().setReq("First request ! ").build());
		finishedLatch.await();
		observer.onCompleted();
	}
}
