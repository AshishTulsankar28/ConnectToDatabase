/**
 * 
 */
package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents database object.
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student",schema = "PUBLIC")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "firstName",length = 60)
	private String firstName;
	
	@Column(name = "lastName",length = 100)
	private String lastName;
	
	@Column(name = "dept",length = 50)
	private String dept;
	
	@Column(name = "address",length = 150)
	private String address;

}
