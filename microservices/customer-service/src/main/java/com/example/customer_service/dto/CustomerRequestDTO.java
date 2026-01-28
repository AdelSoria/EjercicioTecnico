package com.example.customer_service.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {

	@NotNull
	private Long customerId;

	@NotBlank
	private String password;

	private String gender;
	private String identification;
	private String address;
	private String phone;
	private String name;
}
