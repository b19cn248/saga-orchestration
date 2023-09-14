package com.example.inventoryservice.service.impl;

import com.example.dto.InventoryRequestDTO;
import com.example.dto.InventoryResponseDTO;
import com.example.enums.InventoryStatus;
import com.example.inventoryservice.service.InventoryService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
  private Map<Integer, Integer> productInventoryMap;

  @PostConstruct
  private void init(){
    this.productInventoryMap = new HashMap<>();
    this.productInventoryMap.put(1, 5);
    this.productInventoryMap.put(2, 5);
    this.productInventoryMap.put(3, 5);
  }

  @Override
  public InventoryResponseDTO deduct(InventoryRequestDTO requestDTO) {

    log.info("(deduct) requestDTO:{}", requestDTO);

    int quantity = this.productInventoryMap.getOrDefault(requestDTO.getProductId(), 0);
    InventoryResponseDTO responseDTO = new InventoryResponseDTO();
    responseDTO.setOrderId(requestDTO.getOrderId());
    responseDTO.setUserId(requestDTO.getUserId());
    responseDTO.setProductId(requestDTO.getProductId());
    responseDTO.setStatus(InventoryStatus.UNAVAILABLE);
    if(quantity > 0){
      responseDTO.setStatus(InventoryStatus.AVAILABLE);
      this.productInventoryMap.put(requestDTO.getProductId(), quantity - 1);
    }
    return responseDTO;
  }

  @Override
  public void add(InventoryRequestDTO requestDTO) {
    log.info("(add) requestDTO: {}", requestDTO);
    this.productInventoryMap
          .computeIfPresent(requestDTO.getProductId(), (k, v) -> v + 1);
  }

  @Override
  public Integer getInventory(Integer productId) {
    log.info("(get) productId: {}", productId);
    return this.productInventoryMap.getOrDefault(productId, 0);
  }
}
