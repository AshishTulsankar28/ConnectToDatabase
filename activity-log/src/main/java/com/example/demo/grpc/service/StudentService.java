package com.example.demo.grpc.service;


import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.event.service.EventPublisher;
import com.example.demo.event.service.EventUtil;
import com.example.demo.kafka.service.KafkaProducerService;
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

	private final StudentRepository studentRepository;
	private final LogRepository logRepository;	
	private final KafkaProducerService kafkaProducerService;
	private final EventPublisher eventPublisher;

	@Autowired
	public StudentService(StudentRepository studRepo,
						  LogRepository logRepo,
						  KafkaProducerService producer,
						  EventPublisher publisher) {
		this.studentRepository=studRepo;
		this.logRepository=logRepo;
		this.kafkaProducerService=producer;
		this.eventPublisher=publisher;
	}

    /**
     * Demo method written to check gRPC request- response model
     * @implNote
     * Method on gRPC server which reads ID from received request & sends response back to gRPC client by appending
     * hello to the received request ID. 
     */
	@Override
	public void checkConnection(GetStudentRequest request,StreamObserver<GetStudentResponse> responseObserver) {
		log.info("REQUEST {}",request);
		GetStudentResponse response = GetStudentResponse.newBuilder().setExtraMsg("Hello, "+request.getId()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	/**
	 * Get student info from request data & Save it in database.
	 * Returns studentId as response back to gRPC client.
	 */
	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.MANDATORY,rollbackFor = Exception.class)
	public void createStudent(CreateStudentRequest request, StreamObserver<CreateStudentResponse> responseObserver) {
		
		// Build entity from request data & persist it.
		Student entity=new Student(0, request.getStudent().getFirstName(),
				request.getStudent().getLastName(), request.getStudent().getDept(), request.getStudent().getAddress());
		entity=studentRepository.save(entity);
		
		//Publish the event
        eventPublisher.fireEvent(EventUtil.studentAdded(entity));

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
