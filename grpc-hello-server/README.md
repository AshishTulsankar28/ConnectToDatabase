## gRPC

Implementation of gRPC framework using Java

###### Compilation Error (pom.xml shows error with execution)
```
1. mvn install:install-file -Dpackaging=jar -DgeneratePom=true  -DgroupId=com.google.protobuf   -DartifactId=protobuf-java   -Dfile=ext/protobuf-java-2.5.0.jar -Dversion=2.5.0
2. mvn install:install-file -DgroupId=com.google.protobuf -DartifactId=protoc -Dversion=3.12.1 -Dclassifier=${os.detected.classifier} -Dpackaging=exe -Dfile=ext/protoc-3.12.1-windows-x86_64.exe
3. mvn install:install-file -DgroupId=io.grpc -DartifactId=protoc-gen-grpc-java -Dversion=1.31.0 -Dclassifier=windows-x86_64 -Dpackaging=exe -Dfile=ext/protoc-gen-grpc-java-1.31.0-windows-x86_64.exe
```

###### Run from terminal:
```
* Rebuild                                    : mvn -DskipTests package		
* Run Server                                 : mvn -DskipTests package exec:java -Dexec.mainClass=com.example.grpc.App
* Run Client with basic streaming rpc        : mvn -DskipTests package exec:java -Dexec.mainClass=com.example.grpc.BasicStreamClient
* Run Client with server side streaming rpc  : mvn -DskipTests package exec:java -Dexec.mainClass=com.example.grpc.ServerStreamClient
* Run Client with client side streaming rpc  : mvn -DskipTests package exec:java -Dexec.mainClass=com.example.grpc.ClientStreamClient
* Run Client with bidirectional streaming rpc: mvn -Dexec.cleanupDaemonThreads=false -DskipTests package exec:java -Dexec.mainClass=com.example.grpc.BidirStreamClient
```

## See

* [Java Guide](https://www.grpc.io/docs/languages/java/basics/)
* [Issue References](https://github.com/netty/netty/issues/7817) - Netty behaviour
* [Quick Start](https://codelabs.developers.google.com/codelabs/cloud-grpc-java/index.html#0) 





