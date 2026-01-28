package com.example.account_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.account_service.domain.Account;
import com.example.account_service.domain.Movement;
import com.example.account_service.dto.MovementRequestDTO;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;
import com.example.account_service.services.impl.MovementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {

	@Mock
	private MovementRepository movementRepository;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private MovementServiceImpl movementService;

	private Account account;

	@BeforeEach
	void setUp() {
		account = new Account();
		account.setId(1L);
		account.setInitialBalance(new BigDecimal("1000.00"));
		account.setState(true);
	}

	@Test
	void createCreditMovement() {

		MovementRequestDTO dto = new MovementRequestDTO();
		dto.setAccountId(1L);
		dto.setAmount(new BigDecimal("200.00"));
		dto.setType("CREDIT");

		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var response = movementService.create(dto);

		assertEquals(new BigDecimal("1200.00"), response.getBalance());
		verify(movementRepository, times(1)).save(any(Movement.class));
	}

	@Test
	void createDebitMovement() {

		MovementRequestDTO dto = new MovementRequestDTO();
		dto.setAccountId(1L);
		dto.setAmount(new BigDecimal("300.00"));
		dto.setType("DEBIT");

		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var response = movementService.create(dto);

		assertEquals(new BigDecimal("700.00"), response.getBalance());
	}

	@Test
	void whenAccountNotFound() {

		MovementRequestDTO dto = new MovementRequestDTO();
		dto.setAccountId(99L);
		dto.setAmount(new BigDecimal("100.00"));
		dto.setType("CREDIT");

		when(accountRepository.findById(99L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> movementService.create(dto));

		assertEquals("Account not found", exception.getMessage());
	}

	@Test
	void whenUpdateAmountInvalid() {

		Movement movement = new Movement();
		movement.setId(1L);

		MovementRequestDTO dto = new MovementRequestDTO();
		dto.setAmount(BigDecimal.ZERO);

		when(movementRepository.findById(1L)).thenReturn(Optional.of(movement));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> movementService.update(1L, dto));

		assertEquals("The value must be greater than zero", exception.getMessage());
	}

	@Test
	void deleteMovement() {

		Movement movement = new Movement();
		movement.setId(1L);

		when(movementRepository.findById(1L)).thenReturn(Optional.of(movement));

		movementService.delete(1L);

		verify(movementRepository, times(1)).delete(movement);
	}

}
