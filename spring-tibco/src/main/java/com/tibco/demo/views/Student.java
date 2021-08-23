/**
 * 
 */
package com.tibco.demo.views;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ashish Tulsankar
 * 29-Sep-2020
 */
@Data
public class Student implements Serializable {

	private static final long serialVersionUID = -3405858402185569188L;
	private int id;	
	private String firstName;
	private String lastName;
	private String dept;
	private String address;
	
}
