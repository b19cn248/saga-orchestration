package com.example.orderorchestrator.controller;

import com.example.dto.OrchestratorRequestDTO;
import com.example.orderorchestrator.service.OrchestratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class DemoController {

  private final OrchestratorService orchestratorService;

  @KafkaListener(topics = "create-order", groupId = "my-group")
  public void consumeMessage(String message) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    OrchestratorRequestDTO requestDTO = objectMapper.readValue(message, OrchestratorRequestDTO.class);
    orchestratorService.orderProduct(requestDTO);
    System.out.println("Received message: " + message);
  }
}
