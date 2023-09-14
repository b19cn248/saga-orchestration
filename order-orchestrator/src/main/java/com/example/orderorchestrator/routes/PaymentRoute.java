package com.example.orderorchestrator.routes;

import com.example.dto.PaymentRequestDTO;
import com.example.dto.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment-service", url = "http://localhost:8081/api/v1/payments")
public interface PaymentRoute {

  @PostMapping("/debit")
  PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO);

  @PostMapping("/credit")
  void credit(@RequestBody PaymentRequestDTO requestDTO);
}
