/**
 * 
 */
package services;


import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	Logger logger=LogManager.getLogger();


	@Override
	public String getEmpName(int empId) {

		String empName=null;
		try {
			Session session=HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query=session.createQuery("SELECT concat(COALESCE(firstName,''),CONCAT(' ',COALESCE(lastName,''))) FROM Employees WHERE empNo= :empNo");
			query.setParameter("empNo", empId );
			empName=query.getSingleResult().toString();
			session.close();

		} catch (NoResultException e) {
			logger.trace(e);
		}
		return empName;
	}

	@Override
	public Employees getEmpDetails(int empId) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		return session.get(Employees.class, empId);
	}

	@Override
	public int addEmp(Employees emp) {
		int empId=-1;

		try {
			Session session=HibernateUtil.getSessionFactory().openSession();
			Transaction tr=session.beginTransaction();
			emp.setEmpNo(getMaxEmpId()+1);
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

		@SuppressWarnings("rawtypes")
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
	public int persistEmp(Employees emp) {
		int empId=-1;
		try {
			emp.setEmpNo(getMaxEmpId()+1);
			empRepository.save(emp);
			empId = emp.getEmpNo();
		} catch (IllegalArgumentException e) {
			logger.trace(e);
		}
		return empId;
	}

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = false)
	public boolean updateEmp(Employees emp) {
		Employees origEmp=findEmpById(emp.getEmpNo());
		if (origEmp!=null) {
			origEmp.setBirthDate(emp.getBirthDate());
			origEmp.setGender(emp.getGender());
			origEmp.setHireDate(emp.getHireDate());
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteEmp(int empId) {
		
		try {
			Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
			Transaction tr=session.beginTransaction();
			Employees empToDel=hibernateTemplate.get(Employees.class, empId);
			if(empToDel==null) {
				return false;
			}
			
			hibernateTemplate.delete(empToDel);
			tr.commit();
			session.close();
			
		} catch (DataAccessException e) {
			logger.trace(e);
			return false;
		}
		
		return true;
	}


}
