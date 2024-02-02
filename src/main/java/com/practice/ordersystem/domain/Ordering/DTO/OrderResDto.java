package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {
    private Long memberId;
    private String status;
    private LocalDateTime createdTime;
}
