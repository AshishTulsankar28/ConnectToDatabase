package connectViaORM;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Transaction;

import org.hibernate.Session;


/**
 * Program to connect with MySQL server using Hibernate as ORM tool
 * @author Ashish Tulsankar
 *
 */
public class ConnectToDB {

	public void useCriteria(Session session) {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Student> cr = cb.createQuery(Student.class);
		Root<Student> root = cr.from(Student.class);
		cr.select(root);
		 
		List<Student> results = session.createQuery(cr).getResultList();
		for (Student student : results) {
			System.out.println("ROWS "+student.getStudNm());
		}
	}

	public void useSession(Session session, Transaction trans) {
		Student stud=new Student(3, "Nagarjuna", " Programming Analyst", "Vellator");
		session.saveOrUpdate(stud);
		trans.commit();
		
	}

	public void useHQL(Session session, Transaction trans) {
		// HQL
		Query q=session.createQuery("FROM Student");
		List<Student> result=q.getResultList();
		for (Student student : result) {
			System.out.println("DATA : "+student.getStudNm());
		}
	}

}
