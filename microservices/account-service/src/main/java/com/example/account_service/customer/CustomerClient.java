package com.example.account_service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.account_service.dto.CustomerDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerClient {
	@Autowired
    private  RestTemplate restTemplate;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public void validateCustomer(Long customerId) {
        try {
            restTemplate.getForObject(customerServiceUrl + "/" + customerId, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Customer not found or inactive in customer-service");
        }
    }
    
    public CustomerDTO getCustomer(Long customerId) {
        try {
            return restTemplate.getForObject(customerServiceUrl + "/" + customerId, CustomerDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Customer not found or inactive in customer-service");
        }
    }
}
