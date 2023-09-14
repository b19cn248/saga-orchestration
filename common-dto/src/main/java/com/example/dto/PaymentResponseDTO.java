package com.example.dto;

import com.example.enums.PaymentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentResponseDTO {
  private Integer userId;
  private UUID orderId;
  private Double amount;
  private PaymentStatus status;
}
