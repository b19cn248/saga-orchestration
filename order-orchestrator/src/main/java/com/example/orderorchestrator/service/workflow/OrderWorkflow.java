package com.example.orderorchestrator.service.workflow;

import com.example.orderorchestrator.service.steps.WorkflowStep;

import java.util.List;

public class OrderWorkflow implements Workflow {
  private final List<WorkflowStep> steps;

  public OrderWorkflow(List<WorkflowStep> steps) {
    this.steps = steps;
  }

  @Override
  public List<WorkflowStep> getSteps() {
    return this.steps;
  }
}
