# GitHub To Push Your Ideas

Committed projects goes to the repository

## 1.ConnectToDB

Project includes ways to connect with underlying databases.
In current Project, we have used MySQL as RDBMS and utilized basic JDBC connectivity as well as Hibernate ORM tool.

```
1. Connectivity using JDBC API
2. Connectivity using Hibernate
```

## 2.CoreJava
Simple Java programs referred from HackerRank

## 3.JUnitIntro

Simple demo project for unit testing with JUnit5 for Java EE applications. 

## 4.Registrar

Project includes simple implementation of data manipulation (CRUD) using Hibernate.

## 5.SpringMVC-DispatcherDemo

Demo project which illustrates dispatcher servlet in Spring MVC.

## 6.webTester

Project demonstrates unit as well as integration testing for REST APIs in Spring MVC project.
```
1. Junit4
```

## 7.grpc-hello-server

Demo of grpc with Java.
###### Compilation Error (pom.xml shows error with execution)
```
1. mvn install:install-file -Dpackaging=jar -DgeneratePom=true  -DgroupId=com.google.protobuf   -DartifactId=protobuf-java   -Dfile=ext/protobuf-java-2.5.0.jar -Dversion=2.5.0
2. mvn install:install-file -DgroupId=com.google.protobuf -DartifactId=protoc -Dversion=3.12.1 -Dclassifier=${os.detected.classifier} -Dpackaging=exe -Dfile=ext/protoc-3.12.1-windows-x86_64.exe
3. mvn install:install-file -DgroupId=io.grpc -DartifactId=protoc-gen-grpc-java -Dversion=1.31.0 -Dclassifier=windows-x86_64 -Dpackaging=exe -Dfile=ext/protoc-gen-grpc-java-1.31.0-windows-x86_64.exe
```
* [Reference](https://codelabs.developers.google.com/codelabs/cloud-grpc-java/index.html#0)

## 8.web-server

Project basically includes configuration and designing of web APIs.

```
1. Annotation based web configuration for servlet dispatcher.
2. ObjectMapper integration 
3. CORS configuration
4. Declarative Transaction Manager
5. Intro to HibernateTemplate
6. Intro to Spring Data Repository
7. Web APIs designed for CRUD operations using different approaches.
8. HikariCP configuration implemented
```

## 9.Android Appliation

Android application that is designed for vehicle(Fleet) management. This application enables the user to create authentication base secure login using firebase & let the registered admins(e.g. owner of travel agency) track the other application users(drivers under registered agency) in real-time on google maps. Additionally it also enables all to communicate in real-time which is achieved by using Pub Nub APIs.It includes various useful features such as alerting and alarming on overspeeding.
Note: Android application was designed for Android 7.0 so it may warn you on new version as it's intended to use on lower Android version. Some features may not work as online hosted DB may no longer connect & may result in timeout.

## Built With

* [Web MVC](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Hibernate](https://hibernate.org/orm/) - Used as ORM tool
* [Hikari DS](https://github.com/brettwooldridge/HikariCP) - Hikari CP

## Authors

***Ashish Parashram Tulsankar*** - *Initial work* - [JavaCodeGround](https://github.com/AshishTulsankar28/JavaCodeGround/)


