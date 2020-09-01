/**
 * 
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Outbox;

/**
 * 
 * @author Ashish Tulsankar
 * 31-Aug-2020
 */
@Repository
public interface OutboxRepository extends JpaRepository<Outbox, String> {

}
