/**
 * 
 */
package com.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Tulsankar
 * Created on 25-Aug-2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DATABASECHANGE_LOG",schema = "PUBLIC")
public class DatabaseChangeLog {
	
	@Id
	private String ID;
	private String AUTHOR;
	private String FILENAME;
	private Timestamp DATEEXECUTED;
	private int ORDEREXECUTED;
	private String EXECTYPE;
	private String MD5SUM;
	private String DESCRIPTION;
	private String COMMENTS;
	private String TAG;
	private String LIQUIBASE;
	private String CONTEXTS;
	private String LABELS;
	private String DEPLOYMENT_ID;
	

}
