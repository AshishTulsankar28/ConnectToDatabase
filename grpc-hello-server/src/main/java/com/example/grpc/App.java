package com.example.grpc;

import io.grpc.*;

/**
 * Class represents the gRPC server
 * @author Ashish Tulsankar
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	// Create a new server to listen on port 8080
        Server server = ServerBuilder.forPort(8080)
          .addService(new GreetingServiceImpl())
          .build();

        // Start the server
        server.start();

        // Server threads are running in the background.
        System.out.println("gRPC server started successfully !");
        
        // Don't exit the main thread, Wait until server is terminated manually. Also see awaitTermination(long timeout, TimeUnit unit).
        server.awaitTermination();
    }
}
