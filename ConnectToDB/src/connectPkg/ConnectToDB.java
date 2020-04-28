/**
 * 
 */
package connectPkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Ashish Tulsankar
 * Simple program to connect with MySQL server using JDBC driver.
 */
public class ConnectToDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url="jdbc:mysql://localhost:3306/ashish";
		String user="root";
		String password="root";
		Connection connect;
		try {
			connect = DriverManager.getConnection(url, user, password);
			Statement stmt= connect.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM student");
			while(rs.next()) {
				System.out.println("Roll no :"+rs.getInt("studId")+" Name: "+rs.getString("studNm"));
			}
			connect.close();
		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}
		

	}

}
