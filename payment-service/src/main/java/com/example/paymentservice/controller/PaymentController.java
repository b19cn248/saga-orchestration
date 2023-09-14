package com.example.paymentservice.controller;


import com.example.dto.PaymentRequestDTO;
import com.example.dto.PaymentResponseDTO;
import com.example.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {
  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping("/debit")
  public PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO) {
    log.info("(debit) requestDTO: {}", requestDTO);
    return this.service.debit(requestDTO);
  }

  @PostMapping("/credit")
  public void credit(@RequestBody PaymentRequestDTO requestDTO) {
    log.info("(credit) requestDTO:{}", requestDTO);
    this.service.credit(requestDTO);
  }


  @GetMapping("/{userId}")
  public Double getBalance(@PathVariable final Integer userId) {
    log.info("(getBalance) userId:{}", userId);
    return this.service.getBalance(userId);
  }
}
