package com.example.orderorchestrator.service.impl;


import com.example.dto.InventoryRequestDTO;
import com.example.dto.OrchestratorRequestDTO;
import com.example.dto.OrchestratorResponseDTO;
import com.example.dto.PaymentRequestDTO;
import com.example.enums.OrderStatus;
import com.example.orderorchestrator.routes.InventoryRoute;
import com.example.orderorchestrator.routes.PaymentRoute;
import com.example.orderorchestrator.service.OrchestratorService;
import com.example.orderorchestrator.service.steps.WorkflowStep;
import com.example.orderorchestrator.service.steps.WorkflowStepStatus;
import com.example.orderorchestrator.service.steps.impl.InventoryStepImpl;
import com.example.orderorchestrator.service.steps.impl.PaymentStepImpl;
import com.example.orderorchestrator.service.workflow.OrderWorkflow;
import com.example.orderorchestrator.service.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrchestratorServiceImpl implements OrchestratorService {
  private final InventoryRoute inventoryRoute;
  private final PaymentRoute paymentRoute;
  public OrchestratorResponseDTO orderProduct(final OrchestratorRequestDTO requestDTO) {


    log.info("(orderProduct) requestDTO:{}", requestDTO);

    Workflow orderWorkflow = this.getOrderWorkflow(requestDTO);

    List<WorkflowStep> steps = orderWorkflow.getSteps();
    for (WorkflowStep step : steps) {
      boolean result = step.process();

      if (!result) {
        try {
          return revertOrder(orderWorkflow, requestDTO);
        } catch (Exception ex) {
          throw new RuntimeException("Failed to revert order!", ex);
        }
      }
    }
    return getResponseDTO(requestDTO, OrderStatus.ORDER_COMPLETED);
  }

  private OrchestratorResponseDTO revertOrder(final Workflow workflow, final OrchestratorRequestDTO requestDTO) {
    List<WorkflowStep> steps = workflow.getSteps();
    for (WorkflowStep step : steps) {
      if (WorkflowStepStatus.COMPLETE.equals(step.getStatus())) {
        boolean success = false;
        int retries = 0;

        while (!success && retries < 3) {
          try {
            step.revert();
            success = true;
          } catch (Exception e) {
            retries++;
            if (retries == 3) { // Nếu đã thử 3 lần mà vẫn thất bại
              throw new RuntimeException("Failed to revert step after 3 attempts!", e);
            }
          }
        }
      }
    }
    return getResponseDTO(requestDTO, OrderStatus.ORDER_CANCELLED);
  }

  private Workflow getOrderWorkflow(OrchestratorRequestDTO requestDTO) {
    WorkflowStep paymentStep = new PaymentStepImpl(this.paymentRoute, this.getPaymentRequestDTO(requestDTO));
    WorkflowStep inventoryStep = new InventoryStepImpl(this.inventoryRoute, this.getInventoryRequestDTO(requestDTO));
    return new OrderWorkflow(List.of(paymentStep, inventoryStep));
  }

  private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, OrderStatus status) {
    OrchestratorResponseDTO responseDTO = new OrchestratorResponseDTO();
    responseDTO.setOrderId(requestDTO.getOrderId());
    responseDTO.setAmount(requestDTO.getAmount());
    responseDTO.setProductId(requestDTO.getProductId());
    responseDTO.setUserId(requestDTO.getUserId());
    responseDTO.setStatus(status);
    return responseDTO;
  }

  private PaymentRequestDTO getPaymentRequestDTO(OrchestratorRequestDTO requestDTO) {
    PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
    paymentRequestDTO.setUserId(requestDTO.getUserId());
    paymentRequestDTO.setAmount(requestDTO.getAmount());
    paymentRequestDTO.setOrderId(requestDTO.getOrderId());
    return paymentRequestDTO;
  }

  private InventoryRequestDTO getInventoryRequestDTO(OrchestratorRequestDTO requestDTO) {
    InventoryRequestDTO inventoryRequestDTO = new InventoryRequestDTO();
    inventoryRequestDTO.setUserId(requestDTO.getUserId());
    inventoryRequestDTO.setProductId(requestDTO.getProductId());
    inventoryRequestDTO.setOrderId(requestDTO.getOrderId());
    return inventoryRequestDTO;
  }
}
