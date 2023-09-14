package com.example.orderservice.controller;

import com.example.dto.OrchestratorRequestDTO;
import com.example.dto.OrchestratorResponseDTO;
import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;

  @PostMapping
  public Order create(@RequestBody OrderRequestDTO requestDTO){
    return this.service.create(requestDTO);
  }

  @GetMapping
  public List<OrderResponseDTO> getOrders(){
    return this.service.getAll();
  }

  @KafkaListener(topics = "update-order", groupId = "my-group")
  public void consumeMessage(String message) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    OrchestratorResponseDTO responseDTO = objectMapper.readValue(message, OrchestratorResponseDTO.class);
    service.update(responseDTO);
    System.out.println("Received message from orderService: " + message);
  }
}
