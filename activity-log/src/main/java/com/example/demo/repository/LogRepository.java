/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DatabaseChangeLog;

/**
 * @author Ashish Tulsankar
 * Created on 25-Aug-2020
 */
@Repository
public interface LogRepository extends JpaRepository<DatabaseChangeLog, String> {

}
