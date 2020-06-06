/**
 * 
 */
package services;


import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import config.HibernateUtil;
import repositories.EmpRepository;

import views.Employees;

/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class EmpServiceImpl implements EmpService {

	
	@Autowired
	private EmpRepository empRepository;
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	//Logger logger=LogManager.getLogger();
	
	public EmpServiceImpl() {
		//logger.trace("WEBSERVER - EmpServiceImpl Default Constructor invoked ");
	}

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
			//logger.trace("Exception occured in addEmp method"+e);
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

	@Override
	public Employees findEmpById(int empId) {
		Optional<Employees> e=empRepository.findById(empId);
		if(e.isPresent()) {
			return e.get();	
		}
		return null;
	}

	@Override
	public void persistEmp(Employees emp) {
		empRepository.save(emp);
	}

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = false)
	public void updateEmp(Employees emp) {
		Employees origEmp=findEmpById(emp.getEmpNo());

		origEmp.setFirstName(emp.getFirstName());
		origEmp.setBirthDate(emp.getBirthDate());
		origEmp.setLastName(emp.getLastName());
		origEmp.setGender(emp.getGender());
		origEmp.setHireDate(emp.getHireDate());
		
	}

	@Override
	public void deleteEmp(int empId) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction tr=session.beginTransaction();
		hibernateTemplate.delete(hibernateTemplate.get(Employees.class, empId));
		tr.commit();
		session.close();
	}
	
	
}
