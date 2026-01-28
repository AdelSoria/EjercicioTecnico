package com.example.account_service.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.account_service.customer.CustomerClient;
import com.example.account_service.domain.Account;
import com.example.account_service.domain.Movement;
import com.example.account_service.dto.AccountStatementReportDTO;
import com.example.account_service.dto.CustomerDTO;
import com.example.account_service.repository.MovementRepository;
import com.example.account_service.services.ReportService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{
	@Autowired
	private MovementRepository movementRepository;
	@Autowired
	private CustomerClient customerClient;

	/**
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return   {@link List}<{@link AccountStatementReportDTO}> list response
	 */
	public List<AccountStatementReportDTO> generateReport(Long clientId, LocalDate startDate, LocalDate endDate) {

		if (startDate.isAfter(endDate)) {
			throw new RuntimeException("Start date cannot be after end date");
		}

		LocalDateTime start = startDate.atStartOfDay();
		LocalDateTime end = endDate.atTime(23, 59, 59);

		List<Movement> movements = movementRepository.findByClientIdAndDateBetween(clientId, start, end);

		return movements.stream().map(this::mapToDTO).toList();
	}

	/**
	 * Receives an object and returns an object
	 * 
	 * @param    {@link Movement} DTO of request  
	 * @return   {@link AccountStatementReportDTO} DTO of response
	 */
	private AccountStatementReportDTO mapToDTO(Movement movement) {

		Account account = movement.getAccount();
		CustomerDTO customer = customerClient.getCustomer(account.getCustomerId());

		AccountStatementReportDTO dto = new AccountStatementReportDTO();

		dto.setDate(movement.getDate());
		dto.setClient(customer.getName()); 
		dto.setAccountNumber(account.getNumber());
		dto.setAccountType(account.getType());
		dto.setInitialBalance(account.getInitialBalance());
		dto.setState(account.getState());
		dto.setMovementAmount(movement.getAmount());
		dto.setMovementType(movement.getType());
		dto.setAvailableBalance(movement.getBalance());

		return dto;
	}
}
