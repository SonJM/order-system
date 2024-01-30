package com.practice.ordersystem.domain.Member.DTO;

import com.practice.ordersystem.domain.Ordering.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Order;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderListResDto {
    private String name;
    private Long orderId;
    private String orderStatus;
}
