package com.example.orderservice.entity;

import com.example.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  @Id
  private UUID id;
  private Integer userId;
  private Integer productId;
  private Double price;
  private OrderStatus status;

  @PrePersist
  public void prePersist() {
    this.id = UUID.randomUUID();
  }

}
