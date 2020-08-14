/**
 * 
 */
package com.grpc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * gRPC server
 * @author Ashish Tulsankar
 *
 */
public class App {
	private static Logger log = LogManager.getLogger(App.class);

	public static void main( String[] args ) throws Exception
	{

		Server server = ServerBuilder.forPort(8080)
				.addService(new CallServiceGrpcImpl())
				.build();


		server.start();
		log.info("gRPC server started successfully | Server Details:{}:{}",server.getListenSockets(),server.getPort());
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.shutdown();
			log.info("Shutting down server");
		}));
		
		// Don't exit the main thread, Wait until server is terminated manually. Also see awaitTermination(long timeout, TimeUnit unit).
		server.awaitTermination();
	}


}
