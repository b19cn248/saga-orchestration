package com.example.inventoryservice.controller;

import com.example.dto.InventoryRequestDTO;
import com.example.dto.InventoryResponseDTO;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@Slf4j
public class InventoryController {
  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @PostMapping("/deduct")
  public InventoryResponseDTO deduct(@RequestBody final InventoryRequestDTO requestDTO){
    log.info("(deduct) requestDTO:{}", requestDTO);
    return inventoryService.deduct(requestDTO);
  }

  @PostMapping("/add")
  public void add(@RequestBody final InventoryRequestDTO requestDTO){
    log.info("(add) requestDTO: {}", requestDTO);
    inventoryService.add(requestDTO);
  }

  @GetMapping("/{productId}")
  public Integer get(@PathVariable final Integer productId) {
    log.info("(get) productId: {}", productId);
    return inventoryService.getInventory(productId);
  }

}