package com.example.orderorchestrator.service.steps.impl;

import com.example.dto.PaymentRequestDTO;
import com.example.dto.PaymentResponseDTO;
import com.example.enums.PaymentStatus;
import com.example.orderorchestrator.routes.PaymentRoute;
import com.example.orderorchestrator.service.steps.WorkflowStep;
import com.example.orderorchestrator.service.steps.WorkflowStepStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PaymentStepImpl implements WorkflowStep {
  private final PaymentRoute paymentRoute;
  private final PaymentRequestDTO requestDTO;
  private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;


  @Override
  public WorkflowStepStatus getStatus() {
    return this.stepStatus;
  }

  @Override
  public Boolean process() {

    log.info("process in PaymentServiceImpl");

    PaymentResponseDTO paymentResponseDTO = this.paymentRoute.debit(this.requestDTO);
    this.stepStatus = paymentResponseDTO.getStatus().equals(PaymentStatus.PAYMENT_APPROVED) ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED;

    return this.stepStatus.equals(WorkflowStepStatus.COMPLETE);
  }

  @Override
  public Boolean revert() {
    log.info("revert in PaymentServiceImpl");
    this.paymentRoute.credit(this.requestDTO);
    this.stepStatus = WorkflowStepStatus.FAILED;
    return true;
  }
}
