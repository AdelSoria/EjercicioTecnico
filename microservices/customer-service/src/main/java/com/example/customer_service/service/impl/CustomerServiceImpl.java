package com.example.customer_service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer_service.domain.Customer;
import com.example.customer_service.dto.CustomerRequestDTO;
import com.example.customer_service.dto.CustomerResponseDTO;
import com.example.customer_service.dto.CustomerUpdateDTO;
import com.example.customer_service.exception.CustomerNotFoundException;
import com.example.customer_service.repository.CustomerRepository;
import com.example.customer_service.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository repository;

	/**
	 * Delete Customer
	 * 
	 * @param id
	 */
	public void deleteCustomer(Long id) {

		Customer customer = repository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
		repository.delete(customer);
	}

	/**
	 * List all Customers
	 * 
	 * @return {@link List}<{@link Customer}> list response
	 */
	public List<CustomerResponseDTO> listCustomers() {
		return repository.findAll().stream().map(this::mapToResponse).toList();
	}

	/**
	 * Create Customer
	 * 
	 * @param {@link CustomerRequestDTO} DTO of request
	 * @return {@link CustomerResponseDTO} DTO of response
	 */
	public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

		Customer customer = new Customer();
		customer.setCustomerId(dto.getCustomerId());
		customer.setName(dto.getName());
		customer.setPassword(dto.getPassword());
		customer.setGender(dto.getGender());
		customer.setIdentification(dto.getIdentification());
		customer.setAddress(dto.getAddress());
		customer.setPhone(dto.getPhone());
		customer.setState(true);

		Customer saved = repository.save(customer);

		return mapToResponse(saved);
	}

	/**
	 * Receives an object and returns an object
	 * 
	 * @param {@link Customer} DTO of request
	 * @return {@link CustomerResponseDTO} DTO of response
	 */
	private CustomerResponseDTO mapToResponse(Customer customer) {
		return new CustomerResponseDTO(customer.getId(), customer.getCustomerId(), customer.getIdentification(),
				customer.getGender(), customer.getAddress(), customer.getPhone(), customer.getState(),
				customer.getName());
	}

	/**
	 * @param id
	 * @param {@link CustomerUpdateDTO} DTO of request
	 * @return {@link CustomerResponseDTO} DTO of response
	 */
	public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO dto) {

		Customer customer = repository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

		if (dto.getName() != null) {
			customer.setName(dto.getName());
		}
		if (dto.getGender() != null) {
			customer.setGender(dto.getGender());
		}

		if (dto.getIdentification() != null) {
			customer.setIdentification(dto.getIdentification());
		}

		if (dto.getAddress() != null) {
			customer.setAddress(dto.getAddress());
		}

		if (dto.getPhone() != null) {
			customer.setPhone(dto.getPhone());
		}

		if (dto.getState() != null) {
			customer.setState(dto.getState());
		}

		if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
			customer.setPassword(dto.getPassword());
		}

		Customer updated = repository.save(customer);

		return mapToResponse(updated);
	}

	/**
	 * Find Customer by id
	 * 
	 * @param id
	 * @return {@link CustomerResponseDTO} DTO of response
	 */
	public CustomerResponseDTO getCustomerById(Long id) {
		Customer customer = repository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		return mapToResponse(customer);
	}

}
