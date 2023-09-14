package com.example.paymentservice.service;

import com.example.dto.PaymentRequestDTO;
import com.example.dto.PaymentResponseDTO;

public interface PaymentService {

  PaymentResponseDTO debit(final PaymentRequestDTO requestDTO);

  void credit(final PaymentRequestDTO requestDTO);

  Double getBalance(final Integer userId);
}
