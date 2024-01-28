package com.practice.ordersystem.domain.Member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberCreateReqDto {
    String name;
    String email; // unique
    String password;
    String address;
    String role;
}
