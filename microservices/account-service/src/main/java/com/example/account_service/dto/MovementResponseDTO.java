package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MovementResponseDTO {
	private Long id;
	private String type;
	private BigDecimal amount;
	private BigDecimal balance;
	private LocalDateTime date;
}
