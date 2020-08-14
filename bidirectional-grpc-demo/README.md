## gRPC - bidirectional streaming rpc (Ping pong model)

Implementation of gRPC framework using Java

###### Streaming -> bidirectional 
```
Rebuild   : mvn -DskipTests package		
Run Server: mvn -DskipTests package exec:java -Dexec.mainClass=com.grpc.App
Run Client: mvn -Dexec.cleanupDaemonThreads=false -DskipTests package exec:java -Dexec.mainClass=com.grpc.Client

1. mvn install:install-file -Dpackaging=jar -DgeneratePom=true  -DgroupId=com.google.protobuf   -DartifactId=protobuf-java   -Dfile=ext/protobuf-java-2.5.0.jar -Dversion=2.5.0
2. mvn install:install-file -DgroupId=com.google.protobuf -DartifactId=protoc -Dversion=3.12.1 -Dclassifier=${os.detected.classifier} -Dpackaging=exe -Dfile=ext/protoc-3.12.1-windows-x86_64.exe
3. mvn install:install-file -DgroupId=io.grpc -DartifactId=protoc-gen-grpc-java -Dversion=1.31.0 -Dclassifier=windows-x86_64 -Dpackaging=exe -Dfile=ext/protoc-gen-grpc-java-1.31.0-windows-x86_64.exe
```

* [Example Ref](https://github.com/nddipiazza/grpc-java-bidirectional-streaming-example)
* [Issue Ref](https://stackoverflow.com/questions/51871635/couldnt-destroy-threadgroup-org-codehaus-mojo-exec-execjavamojoisolatedthreadg)
* [Issue Ref](https://stackoverflow.com/questions/13471519/running-daemon-with-exec-maven-plugin-avoiding-illegalthreadstateexception)





