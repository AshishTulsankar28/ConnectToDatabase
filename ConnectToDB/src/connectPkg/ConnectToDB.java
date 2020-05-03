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
	 * 
	 * @param query
	 * @param props which should include database name, username and password separated by | respectively
	 * @return 
	 */
	public ResultSet initConnection(String query, String props) {

		String[] dbProp=props.split("\\|");
		String url="jdbc:mysql://localhost:3306/"+dbProp[0];
		Connection connect=null;
		ResultSet rs=null;
		try {
			connect = DriverManager.getConnection(url, dbProp[1], dbProp[2]);
			Statement stmt= connect.createStatement();
			rs= stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("Roll no: "+rs.getInt("studId")+" Name:"+rs.getString("studNm"));
			}
			connect.close();
		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}
		return rs;
	}


}
