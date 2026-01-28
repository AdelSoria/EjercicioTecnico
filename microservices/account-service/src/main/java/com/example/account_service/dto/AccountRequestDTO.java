package com.example.account_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDTO {

	@NotBlank
	private String number;

	@NotBlank
	private String type;

	@NotNull
	@PositiveOrZero
	private BigDecimal initialBalance;

	@NotNull
	private Boolean state;

	@NotNull
	private Long customerId;
}
