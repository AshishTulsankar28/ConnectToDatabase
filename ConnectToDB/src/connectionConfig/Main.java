package connectionConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import connectPkg.ConnectToDB;
import connectViaORM.SessionFactoryInit;

/**
 * 
 * @author Ashish Tulsankar
 * Starting point of the application that demonstrates the ways to connect with DB server
 */
public class Main {

	public static void main(String[] args) {

		// Connected using plain JDBC driver 
		ConnectToDB jdbcConnect= new ConnectToDB();
		jdbcConnect.initConnection("select * from student", "ashish|root|root");


		// Connected using Hibernate
		connectViaORM.ConnectToDB ormConnect=new connectViaORM.ConnectToDB();
		Session session=SessionFactoryInit.getSession().openSession();
		Transaction trans=session.beginTransaction();
		ormConnect.useSession(session,trans );
		ormConnect.useHQL(session, trans);
		ormConnect.useCriteria(session);

	}

}
