/**
 * 
 */
package services;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import repositories.EmpRepository;

import views.Employees;

/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class EmpServiceImpl implements EmpService {

	Logger logger=LogManager.getLogger();

	@Autowired
	private EmpRepository empRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = true)
	public String getEmpName(int empId) {

		String empName=null;
		try {

			Query query=entityManager.createQuery("SELECT concat(COALESCE(firstName,''),CONCAT(' ',COALESCE(lastName,''))) FROM Employees WHERE empNo= :empNo");
			query.setParameter("empNo", empId );
			empName=query.getSingleResult().toString();


		} catch (NoResultException e) {
			logger.trace(e);
		}
		return empName;
	}

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = true)
	public Employees getEmpDetails(int empId) {

		return entityManager.find(Employees.class, empId);
	}

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRES_NEW,readOnly = false)
	public int addEmp(Employees emp) {
		

		try {

			emp.setEmpNo(getMaxEmpId()+1);
			entityManager.persist(emp);
			return emp.getEmpNo();
			

		} catch (Exception e) {
			//logger.trace("Exception occured in addEmp method"+e);
		}


		return -1;
	}

	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = true)
	public int getMaxEmpId() {


		Query query=entityManager.createQuery("SELECT COALESCE(MAX(empNo),-1) FROM Employees");

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
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRES_NEW,readOnly = false)
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
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRES_NEW,readOnly = false)
	public boolean deleteEmp(int empId) {


		
		Employees empToDel=entityManager.find(Employees.class, empId);

		if(empToDel==null) {
			return false;
		}

		entityManager.remove(empToDel);
		

		return true;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED,readOnly = true)
	public List<Employees> getAllEmp(int orderBy,int limit) {

		if(limit>0) {

			Query query=entityManager.createQuery("FROM Employees ORDER BY :orderBy LIMIT :limit");
			query.setParameter("orderBy", 3);
			query.setParameter("limit", 5);

			return query.getResultList();

		}
		
		return empRepository.findAll();


	}


}
