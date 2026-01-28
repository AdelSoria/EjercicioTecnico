package com.example.account_service.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String number;

	@Column(nullable = false)
	private String type; // SAVINGS / CURRENT

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal initialBalance;

	@Column(nullable = false)
	private Boolean state;
	
	@Column(nullable = false)
    private Long customerId;
}
