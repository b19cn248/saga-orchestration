package com.example.orderservice.service.impl;

import com.example.dto.OrchestratorRequestDTO;
import com.example.dto.OrchestratorResponseDTO;
import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.enums.OrderStatus;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  // product price map
  private static final Map<Integer, Double> PRODUCT_PRICE = Map.of(
        1, 100d,
        2, 200d,
        3, 300d
  );

  private final OrderRepository orderRepository;
  private final KafkaTemplate kafkaTemplate;

  public Order create(OrderRequestDTO orderRequestDTO) {
    log.info("Order created: {}", orderRequestDTO);
    Order order = this.orderRepository.save(this.dtoToEntity(orderRequestDTO));
    orderRequestDTO.setOrderId(order.getId());
    this.emitEvent(orderRequestDTO);
    return order;
  }

  public void update(final OrchestratorResponseDTO responseDTO) {
    Order order = this.orderRepository.findById(responseDTO.getOrderId()).get();
    order.setStatus(responseDTO.getStatus());
    this.orderRepository.save(order);
  }

  public List<OrderResponseDTO> getAll() {
    return this.orderRepository.findAll().stream().map(this::entityToDto).toList();
  }

  private void emitEvent(OrderRequestDTO orderRequestDTO) {
    kafkaTemplate.send("create-order", this.getOrchestratorRequestDTO(orderRequestDTO));
  }

  private Order dtoToEntity(final OrderRequestDTO dto) {
    Order order = new Order();
    order.setId(dto.getOrderId());
    order.setProductId(dto.getProductId());
    order.setUserId(dto.getUserId());
    order.setStatus(OrderStatus.ORDER_CREATED);
    order.setPrice(PRODUCT_PRICE.get(order.getProductId()));
    return order;
  }

  private OrderResponseDTO entityToDto(final Order order) {
    OrderResponseDTO dto = new OrderResponseDTO();
    dto.setOrderId(order.getId());
    dto.setProductId(order.getProductId());
    dto.setUserId(order.getUserId());
    dto.setStatus(order.getStatus());
    dto.setAmount(order.getPrice());
    return dto;
  }

  public OrchestratorRequestDTO getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO) {
    OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
    requestDTO.setUserId(orderRequestDTO.getUserId());
    requestDTO.setAmount(PRODUCT_PRICE.get(orderRequestDTO.getProductId()));
    requestDTO.setOrderId(orderRequestDTO.getOrderId());
    requestDTO.setProductId(orderRequestDTO.getProductId());
    return requestDTO;
  }

}
