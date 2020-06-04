/**
 * 
 */
package services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import config.HibernateUtil;
import views.Employees;

/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class EmpServiceImpl implements EmpService {

	public EmpServiceImpl() {
		logger.trace("WEBSERVER - EmpServiceImpl Default Constructor invoked ");
	}

	Logger logger=LogManager.getLogger();	

	@Override
	public String getEmpName(int empId) {

		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query=session.createQuery("SELECT COALESCE(firstName,'') FROM Employees WHERE empNo= :empNo");
		query.setParameter("empNo", empId );

		return query.getSingleResult().toString();
	}

	@Override
	public Employees getEmpDetails(int empId) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		return session.get(Employees.class, empId);
	}

	@Override
	public int addEmp(Employees emp) {
		int empId=0;

		try {
			Session session=HibernateUtil.getSessionFactory().openSession();
			Transaction tr=session.beginTransaction();
			empId = (int)session.save(emp);
			tr.commit();
			session.close();
		} catch (Exception e) {
			logger.trace("Exception occured in addEmp method"+e);
		}


		return empId;
	}

	@Override
	public int getMaxEmpId() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query=session.createQuery("SELECT COALESCE(MAX(empNo),-1) FROM Employees");

		return (int) query.getSingleResult();
	}

}
