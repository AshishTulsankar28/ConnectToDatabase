package com.example.grpc;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.example.grpc.GreetingServiceOuterClass.HelloRequest;
import com.example.grpc.GreetingServiceOuterClass.HelloResponse;

import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest{

	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
	private static Logger log = LogManager.getLogger(AppTest.class);
	private String serverName;

	@Before
	public void setUp() throws Exception{
		
		serverName=InProcessServerBuilder.generateName();
		// Create a server, add service, start, and register for automatic graceful shutdown.
		Server server=grpcCleanup.register(InProcessServerBuilder
				.forName(serverName).directExecutor().addService(new GreetingServiceImpl()).build().start());
		
		log.info("Server started successfully | Server details {}:{}",server.getListenSockets(),server.getPort());

		

	}
	
	@Test
	public void testGreeting() {
		GreetingServiceGrpc.GreetingServiceBlockingStub stub= GreetingServiceGrpc.newBlockingStub(grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
		HelloResponse reply =
				stub.greeting(HelloRequest.newBuilder().setName( "test request").build());

		assertEquals("Hello, test request", reply.getGreeting());
	}
}
