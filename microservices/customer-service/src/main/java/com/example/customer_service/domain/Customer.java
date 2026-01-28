package com.example.customer_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "customer")
public class Customer extends Person {
	@Column(unique = true, nullable = false)
	private Long customerId;
	private String password;
	private Boolean state;
}
