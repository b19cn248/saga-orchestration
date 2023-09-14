package com.example.inventoryservice.service;

import com.example.dto.InventoryRequestDTO;
import com.example.dto.InventoryResponseDTO;

public interface InventoryService {

  InventoryResponseDTO deduct(final InventoryRequestDTO requestDTO);

  void add(final InventoryRequestDTO requestDTO);

  Integer getInventory(final Integer productId);

}
