package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.grpc.generated.CreateStudentRequest;
import com.example.grpc.generated.CreateStudentResponse;
import com.example.grpc.generated.GetStudentRequest;
import com.example.grpc.generated.GetStudentResponse;
import com.example.grpc.generated.Student;
import com.example.grpc.generated.StudentServiceGrpc.StudentServiceBlockingStub;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
/**
 * Represents gRPC client
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Service
public class ClientService {

	private static Logger log= LogManager.getLogger(ClientService.class);

	@GrpcClient("grpc-activity-server")
	private StudentServiceBlockingStub simpleStub;

	public String ping(int studId) {
		try {
			log.info("PING: {}",this.simpleStub.getChannel().authority());
			GetStudentResponse response = this.simpleStub.checkConnection(GetStudentRequest.newBuilder().setId(studId).build());
			return response.getExtraMsg();
		} catch (final StatusRuntimeException e) {
			log.info("ERROR: {}",e);
			return "REQUEST FAILED";
		}
	}
	
	/**
	 * TODO get Student data from request
	 * As of now, creating dummy
	 * @return
	 */
	public void saveStudent() {
		try {
			log.info("gRPC client: {}",this.simpleStub.getChannel().authority());
			Student studProto=Student.newBuilder().setId(1).setFirstName("Saurabh").setLastName("Deshpande").setDept("IT").setAddress("Nashik").build();
			CreateStudentResponse response = this.simpleStub.createStudent(CreateStudentRequest.newBuilder().setStudent(studProto).build());
		    log.info("ID returned as response- {}",response);
		} catch (final StatusRuntimeException e) {
			log.info("ERROR: {}",e);
		}
	}
	
	public void publishChanges() {
		try {
			log.info("gRPC client: {}",this.simpleStub.getChannel().authority());
			//TODO Creating dummy object as of now to invoke method. Change it later.
			Student studProto=Student.newBuilder().setId(1).setFirstName("Saurabh").setLastName("Deshpande").setDept("IT").setAddress("Nashik").build();
			CreateStudentResponse response = this.simpleStub.publishChanges(CreateStudentRequest.newBuilder().setStudent(studProto).build());
		    log.info("DB changes count: {}",response);
		} catch (final StatusRuntimeException e) {
			log.info("ERROR: {}",e);
		}
	}

}
