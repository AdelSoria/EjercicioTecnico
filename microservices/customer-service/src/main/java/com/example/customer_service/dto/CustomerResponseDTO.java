package com.example.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private Long customerId;
    private String identification;
    private String gender;
    private String address;
    private String phone;
    private Boolean state;
    private String name;
}
