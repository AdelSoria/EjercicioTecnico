package com.example.customer_service.dto;

import lombok.Data;

@Data
public class CustomerUpdateDTO {
	private String gender;
	private String identification;
	private String address;
	private String phone;
	private String password;
	private Boolean state;
	private String name;
}
