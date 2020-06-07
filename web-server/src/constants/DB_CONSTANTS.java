package constants;

/**
 * Class that represents constants used for DB related properties
 * @author Ashish Tulsankar
 *
 */
public class DB_CONSTANTS {
	
	public static final String DB_DRIVER="com.mysql.cj.jdbc.Driver";
	
	public static final String DB_URL="jdbc:mysql://localhost:3306/ashish";
	
	public static final String DB_USER="root";
	
	public static final String DB_PWD="root";
	
	public static final String DB_DIALECT="org.hibernate.dialect.MySQL5Dialect";
	/**
	 * Maximum number of actual connection in the pool
	 */
	public static final String MAX_POOL_SIZE="10";
	/**
	 * Maximum time that a connection is allowed to sit ideal in the pool
	 */
	public static final String IDLE_TIMEOUT="30000";
	/**
	 * Maximum waiting time for a connection from the pool
	 */
	public static final String CONNECTION_TIMEOUT="20000";
	/**
	 * Minimum number of ideal connections in the pool
	 */
	public static final String MIN_IDLE="10";
	
	public static final String SESSION_CTX_SCOPE="thread";
	
	public static final String SHOW_SQL="true";
	
	public static final String CONNECTION_PROVIDER="org.hibernate.hikaricp.internal.HikariCPConnectionProvider";
	


}
