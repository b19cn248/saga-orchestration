package com.example.orderservice.service;


import com.example.dto.OrchestratorResponseDTO;
import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.orderservice.entity.Order;

import java.util.List;

public interface OrderService {
  Order create(OrderRequestDTO orderRequestDTO);

  List<OrderResponseDTO> getAll();

  void update(final OrchestratorResponseDTO responseDTO);
}
