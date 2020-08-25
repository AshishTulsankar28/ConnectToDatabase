/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;

/**
 * DAO for {@link Student}
 * @author Ashish Tulsankar
 * Created on 24-Aug-2020
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
