package com.example.grpc;

import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
  @Override
  public void greeting(GreetingServiceOuterClass.HelloRequest request,
        StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver) {
  // HelloRequest has toString auto-generated.
    System.out.println("Request from greeting"+request);

    // You must use a builder to construct a new Protobuffer object
    GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass.HelloResponse.newBuilder()
      .setGreeting("Hello, " + request.getName())
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);

    // When you are done, you must call onCompleted.
    responseObserver.onCompleted();
  }
  @Override
  public void responsiveGreets(GreetingServiceOuterClass.HelloRequest request,
        StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver) {
  // HelloRequest has toString auto-generated.
    System.out.println("request from responsiveGreets"+request);

    // You must use a builder to construct a new Protobuffer object
    GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass.HelloResponse.newBuilder()
      .setGreeting("Hello, " + request.getName())
      .build();

  
    responseObserver.onNext(response);
    responseObserver.onNext(response);
    responseObserver.onNext(response);

    // When you are done, you must call onCompleted.
    responseObserver.onCompleted();
  }
}