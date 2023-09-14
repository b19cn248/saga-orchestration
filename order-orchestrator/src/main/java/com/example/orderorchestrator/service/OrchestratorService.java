package com.example.orderorchestrator.service;

import com.example.dto.OrchestratorRequestDTO;
import com.example.dto.OrchestratorResponseDTO;

public interface OrchestratorService {
  OrchestratorResponseDTO orderProduct(final OrchestratorRequestDTO requestDTO);
}
