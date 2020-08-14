package com.grpc;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

public class CallServiceGrpcImpl extends CallServiceGrpc.CallServiceImplBase  {
	private static Logger log = LogManager.getLogger(App.class);

	@Override
	public StreamObserver<BiDirectionalCallService.RequestCall> connect(StreamObserver<BiDirectionalCallService.ResponseCall> responseObserver) {
		log.info("Connecting to stream observer...");

		//TODO stream closed issue
		ServerCallStreamObserver<BiDirectionalCallService.ResponseCall> scso = (ServerCallStreamObserver<BiDirectionalCallService.ResponseCall>) responseObserver;
		scso.setOnCancelHandler(new Runnable() {
			
			@Override
			public void run() {
				scso.onCompleted();
				log.info("SERVER: Streaming stopped !");  
				
			}
		});
		
		StreamObserver<BiDirectionalCallService.RequestCall> so = new StreamObserver<BiDirectionalCallService.RequestCall>() {

			@Override
			public void onNext(BiDirectionalCallService.RequestCall value) {

				//TODO we will send back received data with current time stamp as response back to client.
				log.info("Received {} from client",value.getReq());
				String msg=value.getReq()+" | "+Instant.now().toString();

				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}


				responseObserver.onNext(BiDirectionalCallService.ResponseCall.newBuilder().setResp(msg).build());
				//log.info("Message sent ! Sending pong to acknowledge");
				//BiDirectionalCallService.ResponseCall reply = BiDirectionalCallService.ResponseCall.newBuilder().setResp("Pong").build();
				//responseObserver.onNext(reply);

			}

			@Override
			public void onError(Throwable t) {
				log.trace("SERVER: on error {}",t);

			}

			@Override
			public void onCompleted() {
				log.info("SERVER: on completed");
			}
		};

		return so;
	}
}
