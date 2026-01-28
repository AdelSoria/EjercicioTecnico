package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovementRequestDTO {
    @NotNull
    private Long accountId;

    @NotBlank
    private String type; // DEBIT / CREDIT
    
    @NotNull
    @Positive(message = "The value must be greater than zero")
    private BigDecimal amount;
}
