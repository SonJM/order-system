package com.practice.ordersystem.domain.OrderItem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemListResDto {
    private Long orderId;
    private String itemName;
    private int quantity;
}
