package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OrderListResDto {
    private Long memberId;
    private String status;
    private LocalDateTime createdTime;
}
