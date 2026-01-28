package com.example.account_service.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDTO {

	private Long id;
	private String number;
	private String type;
	private BigDecimal initialBalance;
	private Boolean state;
	private Long customerId;
}
