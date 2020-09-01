/**
 * 
 */
package com.example.demo.grpc.service;

import java.util.Map;

/**
 * @author Ashish Tulsankar
 *
 */
public interface ClientService {

	public String ping(int studId);
	
	public int saveStudent(Map<String,String> studentInfo);
	
	public void publishChanges();
	
}
