/**
 * 
 */
package com.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents history table created by Liquibase.
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
	@Column(name="id",length = 60)
	private String ID;
	
	@Column(name="author",length = 20)
	private String AUTHOR;
	
	@Column(name="fileName",length = 100)
	private String FILENAME;
	
	@Column(name="dateExecuted")
	private Timestamp DATEEXECUTED;
	
	@Column(name="orderExecuted")
	private int ORDEREXECUTED;
	
	@Column(name="execType",length = 60)
	private String EXECTYPE;
	
	@Column(name="md5sum",length = 60)
	private String MD5SUM;
	
	@Column(name="description",length = 180)
	private String DESCRIPTION;
	
	@Column(name="comments",length = 50)
	private String COMMENTS;
	
	@Column(name="tag",length = 60)
	private String TAG;
	
	@Column(name="liquibase",length = 50)
	private String LIQUIBASE;
	
	@Column(name="contexts",length = 60)
	private String CONTEXTS;
	
	@Column(name="labels",length = 60)
	private String LABELS;
	
	private String DEPLOYMENT_ID;
	

}
