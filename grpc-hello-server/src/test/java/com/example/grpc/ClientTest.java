package com.example.grpc;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import com.example.grpc.GreetingServiceOuterClass.HelloRequest;
import com.example.grpc.GreetingServiceOuterClass.HelloResponse;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;

@RunWith(JUnit4.class)
public class ClientTest {

	private static Logger log = LogManager.getLogger(ClientTest.class);

	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
	private BasicStreamClient client;

	private final GreetingServiceGrpc.GreetingServiceImplBase serviceImpl =
			mock(GreetingServiceGrpc.GreetingServiceImplBase.class, delegatesTo(
					new GreetingServiceGrpc.GreetingServiceImplBase() {
						// By default the client will receive Status.UNIMPLEMENTED for all RPCs.
						// You might need to implement necessary behaviors for your test here, like this:
						@Override
						public void greeting(HelloRequest request, StreamObserver<HelloResponse> respObserver) {
							GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass.HelloResponse.newBuilder()
									.setGreeting("Hey, " + request.getName())
									.build();
							log.info("greeting() method mocked. Response~ {}",response);
							respObserver.onNext(response);
							respObserver.onCompleted();
						}
					}));

	@Before
	public void setUp() throws Exception {
		String serverName = InProcessServerBuilder.generateName();

		grpcCleanup.register(InProcessServerBuilder.forName(serverName).directExecutor()
				.addService(serviceImpl).build().start());

		client = new BasicStreamClient(grpcCleanup
				.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
	}

	@Test
	public void testMessageDelivery() {
		ArgumentCaptor<HelloRequest> requestCaptor = ArgumentCaptor.forClass(HelloRequest.class);
		client.greeting("test name");

		verify(serviceImpl)
		.greeting(requestCaptor.capture(), ArgumentMatchers.<StreamObserver<HelloResponse>>any());

		assertEquals("test name", requestCaptor.getValue().getName());
	}
}
