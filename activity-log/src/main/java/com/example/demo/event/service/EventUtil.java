package com.example.demo.event.service;

import com.example.demo.dto.OutboxEvent;
import com.example.demo.model.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class EventUtil {

	public static OutboxEvent studentAdded(Student studentEntity) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(studentEntity, JsonNode.class);

        return new OutboxEvent(
                studentEntity.getId(),
                "CREATE",
                jsonNode
        );
    }
	
}
