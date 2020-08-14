package com.grpc;

import java.time.Instant;


import io.grpc.stub.StreamObserver;

public class CallServiceGrpcImpl extends CallServiceGrpc.CallServiceImplBase  {
	@Override
    public StreamObserver<BiDirectionalCallService.RequestCall> connect(StreamObserver<BiDirectionalCallService.ResponseCall> responseObserver) {
        System.out.println("Connecting stream observer");
        StreamObserver<BiDirectionalCallService.RequestCall> so = new StreamObserver<BiDirectionalCallService.RequestCall>() {
            @Override
            public void onNext(BiDirectionalCallService.RequestCall value) {
            	//TODO will send back ping with receivers time stamp as response back to client
                String msg=value.getReq()+" received at "+Instant.now().toString();
            	System.out.println("onNext from server, Sending back received "+msg);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
              
                responseObserver.onNext(BiDirectionalCallService.ResponseCall.newBuilder().setResp(msg).build());
                System.out.println("Received message sent back, Sending pong to acknowledge");
				BiDirectionalCallService.ResponseCall reply = BiDirectionalCallService.ResponseCall.newBuilder().setResp("Pong").build();
                responseObserver.onNext(reply);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("on error");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("on completed");
            }
        };
        return so;
    }
}
