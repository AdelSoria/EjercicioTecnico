package com.example.customer_service.service;

import java.util.List;

import com.example.customer_service.dto.CustomerRequestDTO;
import com.example.customer_service.dto.CustomerResponseDTO;
import com.example.customer_service.dto.CustomerUpdateDTO;


public interface CustomerService  {
	
    List<CustomerResponseDTO> listCustomers();

    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);

    CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO dto);

    void deleteCustomer(Long id);

    CustomerResponseDTO getCustomerById(Long id);
}
