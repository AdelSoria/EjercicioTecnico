package com.example.account_service.dto;

import lombok.Data;

@Data
public class CustomerDTO {
	private Long customerId;
	private String name;
	private Boolean state;
}
