package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccountStatementReportDTO {
	private LocalDateTime date;
	private String client;
	private String accountNumber;
	private String accountType;
	private BigDecimal initialBalance;
	private Boolean state;
	private BigDecimal movementAmount;
	private String movementType;
	private BigDecimal availableBalance;
}
