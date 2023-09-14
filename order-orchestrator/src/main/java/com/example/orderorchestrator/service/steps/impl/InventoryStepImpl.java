package com.example.orderorchestrator.service.steps.impl;

import com.example.dto.InventoryRequestDTO;
import com.example.dto.InventoryResponseDTO;
import com.example.enums.InventoryStatus;
import com.example.orderorchestrator.routes.InventoryRoute;
import com.example.orderorchestrator.service.steps.WorkflowStep;
import com.example.orderorchestrator.service.steps.WorkflowStepStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class InventoryStepImpl implements WorkflowStep {
  private final InventoryRoute inventoryRoute;
  private final InventoryRequestDTO requestDTO;
  private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;


  @Override
  public WorkflowStepStatus getStatus() {
    return this.stepStatus;
  }

  @Override
  public Boolean process() {

    log.info("process in InventoryStepImpl");

    InventoryResponseDTO inventoryResponseDTO = this.inventoryRoute.deduct(this.requestDTO);

    this.stepStatus = inventoryResponseDTO.getStatus().equals(InventoryStatus.AVAILABLE) ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED;

    return this.stepStatus.equals(WorkflowStepStatus.COMPLETE);

  }

  @Override
  public Boolean revert() {

    log.info("revert in InventoryStepImpl");

    this.inventoryRoute.add(this.requestDTO);
    this.stepStatus = WorkflowStepStatus.FAILED;
    return true;
  }
}
