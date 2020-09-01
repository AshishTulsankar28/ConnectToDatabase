/**
 * 
 */
package com.example.demo.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Tulsankar
 * Outbox table used in implementation of Outbox pattern
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Outbox",schema = "PUBLIC")
public class Outbox {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "aggregateId")
    private Integer aggregateId;

    @Column(name = "eventType",length = 10)
    private String eventType;

    @Column(name = "payload")
    private String payload;

    @Column(name = "createdOn")
    private Date createdOn;
}
