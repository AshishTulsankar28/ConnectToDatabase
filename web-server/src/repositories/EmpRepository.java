package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import views.Employees;

@Repository
public interface EmpRepository extends JpaRepository<Employees, Integer>  {
	
}
