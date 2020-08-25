/**
 * 
 */
package com.example.demo.model;

/**
 * Constants used for configuring data source
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */
public class DB_CONSTANTS {

	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/ashish";
	public static final String MYSQL_USER="root";
	public static final String MYSQL_PWD = "root";
	
    public static final String H2_DRIVER = "org.h2.Driver";
	public static final String H2_URL = "jdbc:h2:mem:test";
	public static final String H2_USER="sa";
	public static final String H2_PWD = "sa";
	
	public static final String MAX_POOL_SIZE = "10";
	public static final String IDLE_TIMEOUT = "30000";
	public static final String CONNECTION_TIMEOUT = "20000";
	public static final String MIN_IDLE = "10";

}
