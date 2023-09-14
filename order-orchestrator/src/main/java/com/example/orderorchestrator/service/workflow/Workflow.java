package com.example.orderorchestrator.service.workflow;


import com.example.orderorchestrator.service.steps.WorkflowStep;

import java.util.List;

public interface Workflow {
  List<WorkflowStep> getSteps();
}
