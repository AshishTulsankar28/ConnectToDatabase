# spring-tibco

Spring boot project with Tibco implementation. 

```
1. Spring Boot
2. Tibco
```

#### About Tibco

##### TIBCO EMS based messaging, there are two supported models.
> 1. Queue Based Communication
> 2. Topic Based Communication

##### External Dependencies

```
1. mvn install:install-file -DgroupId='com.tibco' -DartifactId='tibjms' -Dversion='4.1' -DgeneratePom=true -Dpackaging=jar -Dfile='D:\Eclipse\TibcoEMS\libs\tibjms-4.1.jar'
2. mvn install:install-file -DgroupId='javax.jms' -DartifactId='jms' -Dversion='1.1' -DgeneratePom=true -Dpackaging=jar -Dfile='D:\Eclipse\TibcoEMS\libs\javax.jms-1.1.jar'
3. mvn install:install-file -DgroupId='org.springframework' -DartifactId='org.springframework.core' -Dversion='3.0.0.RELEASE' -DgeneratePom=true -Dpackaging=jar -Dfile='D:\Eclipse\spring-tibco\libs\org.springframework.core-sources-3.0.0.RELEASE.jar'
```
