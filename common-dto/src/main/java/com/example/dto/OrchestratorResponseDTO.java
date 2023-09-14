package com.example.dto;

import com.example.enums.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OrchestratorResponseDTO {
  private Integer userId;
  private Integer productId;
  private UUID orderId;
  private Double amount;
  private OrderStatus status;
}
