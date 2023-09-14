package com.example.orderorchestrator.service.steps;

public interface WorkflowStep {
  WorkflowStepStatus getStatus();

  Boolean process();

  Boolean revert();
}
