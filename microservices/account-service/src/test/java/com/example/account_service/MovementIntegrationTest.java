package com.example.account_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.account_service.customer.CustomerClient;
import com.example.account_service.domain.Account;
import com.example.account_service.domain.Movement;
import com.example.account_service.dto.CustomerDTO;
import com.example.account_service.dto.MovementRequestDTO;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class MovementIntegrationTest {
    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerClient customerClient; 

    private Account account;
    
    @BeforeEach
    void setup() {

        // Crear cuenta asociada al cliente
        account = new Account();
        account.setNumber("ACC-001");
        account.setType("SAVINGS");
        account.setInitialBalance(new BigDecimal("1000.00"));
        account.setState(true);
        account.setCustomerId(1L);
        accountRepository.save(account);
    }
    
    @Test
    void testCreateCreditMovement() {
        MovementRequestDTO dto = new MovementRequestDTO();
        dto.setAccountId(account.getId());
        dto.setType("CREDIT");
        dto.setAmount(new BigDecimal("500.00"));

        // Validar cliente antes de crear movimiento
       // CustomerDTO customer = customerClient.getCustomer(account.getCustomerId());
       // assertNotNull(customer);

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setType(dto.getType());
        movement.setAmount(dto.getAmount());
        movement.setBalance(account.getInitialBalance().add(dto.getAmount()));
        movement.setDate(LocalDateTime.now());
        movementRepository.save(movement);

        Account updatedAccount = accountRepository.findById(account.getId()).orElseThrow();
        BigDecimal expectedBalance = new BigDecimal("1500.00");
        BigDecimal movementBalance = movement.getBalance();
        assertEquals(expectedBalance, movementBalance);

        List<Movement> movements = movementRepository.findAll();
        assertEquals(1, movements.size());
        assertEquals("CREDIT", movements.get(0).getType());
        assertEquals(new BigDecimal("500.00"), movements.get(0).getAmount());
    }
}
