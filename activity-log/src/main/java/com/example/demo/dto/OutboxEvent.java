/**
 * 
 */
package com.example.demo.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ashish Tulsankar
 * 31-Aug-2020
 */
@Data
@AllArgsConstructor
public class OutboxEvent {
	private Integer aggregateId;
	private String eventType;
	private JsonNode payload;
}
