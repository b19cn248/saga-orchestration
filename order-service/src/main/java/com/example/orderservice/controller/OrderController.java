package com.example.orderservice.controller;

import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
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
}
