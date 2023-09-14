package com.example.orderorchestrator.routes;

import com.example.dto.InventoryRequestDTO;
import com.example.dto.InventoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "inventory-service", url = "http://localhost:8082/api/v1/inventories")
public interface InventoryRoute {
  @PostMapping("/deduct")
  InventoryResponseDTO deduct(@RequestBody final InventoryRequestDTO requestDTO);

  @PostMapping("/add")
  void add(@RequestBody final InventoryRequestDTO requestDTO);
}
