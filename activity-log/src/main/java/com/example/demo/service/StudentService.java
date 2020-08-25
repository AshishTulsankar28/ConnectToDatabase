package com.example.demo.service;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.DatabaseChangeLog;
import com.example.demo.model.Student;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.StudentRepository;
import com.example.grpc.generated.CreateStudentRequest;
import com.example.grpc.generated.CreateStudentResponse;
import com.example.grpc.generated.GetStudentRequest;
import com.example.grpc.generated.GetStudentResponse;
import com.example.grpc.generated.StudentServiceGrpc.StudentServiceImplBase;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Represents gRPC server.
 * @author Ashish Tulsankar
 * Created on 22-Aug-2020
 */

@GrpcService
public class StudentService extends StudentServiceImplBase {
	private static Logger log= LogManager.getLogger(StudentService.class);

	private StudentRepository studentRepository;
	private LogRepository logRepository;	
	private KafkaProducerService kafkaProducerService;

	@Autowired
	public StudentService(StudentRepository studRepo,LogRepository logRepo,KafkaProducerService producer) {
		this.studentRepository=studRepo;
		this.logRepository=logRepo;
		this.kafkaProducerService=producer;
	}


	@Override
	public void checkConnection(GetStudentRequest request,StreamObserver<GetStudentResponse> responseObserver) {
		log.info("REQUEST {}",request);
		GetStudentResponse response = GetStudentResponse.newBuilder().setExtraMsg("Hello, "+request.getId()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void createStudent(CreateStudentRequest request, StreamObserver<CreateStudentResponse> responseObserver) {

		/**
		 * TODO Fetch DB changes from log table & send it to kafka. 
		 * Currently iterating raw list, check possible serialization.
		 */
		// Build entity from request data & persist it.
		Student entity=new Student(request.getStudent().getId(), request.getStudent().getFirstName(),
				request.getStudent().getLastName(), request.getStudent().getDept(), request.getStudent().getAddress());
		studentRepository.save(entity);

		// Returning response
		CreateStudentResponse response = CreateStudentResponse.newBuilder().setId(entity.getId()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	
	@Override
	public void publishChanges(CreateStudentRequest request, StreamObserver<CreateStudentResponse> responseObserver) {

        List<DatabaseChangeLog> dbChanges=logRepository.findAll();
        for (DatabaseChangeLog change : dbChanges) {
			this.kafkaProducerService.sendMessage(change.toString());
		}
		
        // Returning count of records as response
		CreateStudentResponse response = CreateStudentResponse.newBuilder().setId(dbChanges.size()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}
