package com.example.account_service.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.account_service.customer.CustomerClient;
import com.example.account_service.domain.Account;
import com.example.account_service.dto.AccountRequestDTO;
import com.example.account_service.dto.AccountResponseDTO;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.services.AccountService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository repository;
	@Autowired
	private CustomerClient customerClient;

	/**
	 * Delete Account by id
	 *
	 * @param id id Account
	 */
	public void deleteAccount(Long id) {

		Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
		repository.delete(account);
	}

	/**
	 * List all accounts
	 * 
	 * @return {@link List}<{@link AccountResponseDTO}> list response
	 */
	public List<AccountResponseDTO> listAccounts() {
		return repository.findAll().stream().map(this::mapToDTO).toList();
	}

	/**
	 * Account creation
	 * 
	 * @param {@link AccountRequestDTO} DTO of request
	 * @return {@link AccountResponseDTO} DTO of response
	 */
	public AccountResponseDTO create(AccountRequestDTO dto) {

		log.info("Creating account for customerId={}", dto.getCustomerId());

		if (repository.findByNumber(dto.getNumber()).isPresent()) {
			throw new RuntimeException("Account number already exists");
		}

		customerClient.validateCustomer(dto.getCustomerId());

		log.info("Customer {} validated successfully", dto.getCustomerId());

		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setType(dto.getType());
		account.setInitialBalance(dto.getInitialBalance());
		account.setState(dto.getState());
		account.setCustomerId(dto.getCustomerId());

		repository.save(account);

		return mapToDTO(account);
	}

	/**
	 * Receives an object and returns an object
	 * 
	 * @param {@link Account} DTO of request
	 * @return {@link AccountResponseDTO} DTO of response
	 */
	private AccountResponseDTO mapToDTO(Account account) {

		AccountResponseDTO dto = new AccountResponseDTO();
		dto.setId(account.getId());
		dto.setNumber(account.getNumber());
		dto.setType(account.getType());
		dto.setInitialBalance(account.getInitialBalance());
		dto.setState(account.getState());
		dto.setCustomerId(account.getCustomerId());

		return dto;
	}

	/**
	 * update account
	 * 
	 * @param id
	 * @param dto {@link AccountRequestDTO} DTO of request
	 * @return {@link AccountResponseDTO} DTO of response
	 */
	public AccountResponseDTO update(Long id, AccountRequestDTO dto) {

		Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		if (dto.getType() != null)
			account.setType(dto.getType());

		if (dto.getInitialBalance() != null)
			account.setInitialBalance(dto.getInitialBalance());

		if (dto.getState() != null)
			account.setState(dto.getState());

		return mapToDTO(account);
	}

}
