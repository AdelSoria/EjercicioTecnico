package com.example.account_service.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.account_service.domain.Account;
import com.example.account_service.domain.Movement;
import com.example.account_service.dto.MovementRequestDTO;
import com.example.account_service.dto.MovementResponseDTO;
import com.example.account_service.exception.InsufficientBalanceException;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;
import com.example.account_service.services.MovementService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovementServiceImpl implements MovementService {
	@Autowired
    private  MovementRepository movementRepository;
	@Autowired
    private  AccountRepository accountRepository;
	
    /**
     * @param {@link MovementRequestDTO} DTO of request
     * @return {@link MovementResponseDTO} DTO of response
     */
    public MovementResponseDTO create(MovementRequestDTO dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal currentBalance = account.getInitialBalance();
        BigDecimal newBalance;

        if (dto.getType().equalsIgnoreCase("DEBIT")) {

            if (currentBalance.compareTo(dto.getAmount()) < 0) {
                throw new InsufficientBalanceException("Balance not available");
            }

            newBalance = currentBalance.subtract(dto.getAmount());

        } else if (dto.getType().equalsIgnoreCase("CREDIT")) {

            newBalance = currentBalance.add(dto.getAmount());

        } else {
            throw new RuntimeException("Invalid movement type");
        }

        account.setInitialBalance(newBalance);

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(dto.getAmount());
        movement.setType(dto.getType());
        movement.setDate(LocalDateTime.now());
        movement.setBalance(newBalance);

        movementRepository.save(movement);

        return mapToDTO(movement);
    }
    
    /**
      * Receives an object and returns an object
	 * 
	 * @param {@link Movement} DTO of request
	 * @return {@link MovementResponseDTO} DTO of response
     */
    private MovementResponseDTO mapToDTO(Movement movement) {

        MovementResponseDTO dto = new MovementResponseDTO();
        dto.setId(movement.getId());
        dto.setType(movement.getType());
        dto.setAmount(movement.getAmount());
        dto.setBalance(movement.getBalance());
        dto.setDate(movement.getDate());

        return dto;
    }
    
    /**
	 * List all Movements
	 * 
	 * @return  {@link List}<{@link MovementResponseDTO}> list response
	 */
    public List<MovementResponseDTO> list() {
        return movementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    /**
     * update Movement
     * 
     * @param id
     * @param  {@link MovementRequestDTO} DTO of request
     * @return  {@link MovementResponseDTO} DTO of response
     */
    public MovementResponseDTO update(Long id, MovementRequestDTO dto) {

        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement not found"));

        Account account = movement.getAccount();

        BigDecimal oldAmount = movement.getAmount();
        String oldType = movement.getType();

     
        if ("CREDIT".equalsIgnoreCase(oldType)) {
            account.setInitialBalance(account.getInitialBalance().subtract(oldAmount));
        } else if ("DEBIT".equalsIgnoreCase(oldType)) {
            account.setInitialBalance(account.getInitialBalance().add(oldAmount));
        }

       
        if (dto.getAmount() != null) {
            if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("The value must be greater than zero");
            }
            movement.setAmount(dto.getAmount());
        }

        if (dto.getType() != null) {
            movement.setType(dto.getType());
        }

        BigDecimal newAmount = movement.getAmount();
        String newType = movement.getType();

      
        if ("CREDIT".equalsIgnoreCase(newType)) {
            account.setInitialBalance(account.getInitialBalance().add(newAmount));
        } else if ("DEBIT".equalsIgnoreCase(newType)) {

            if (account.getInitialBalance().compareTo(newAmount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            account.setInitialBalance(account.getInitialBalance().subtract(newAmount));
        }

        movement.setBalance(account.getInitialBalance());
        movement.setAccount(account);
      
        movementRepository.save(movement);

        return mapToDTO(movement);
    }
    
    /**
     * eliminates movements
     * 
     * @param id
     */
    public void delete(Long id) {

        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement not found"));

        Account account = movement.getAccount();

        BigDecimal oldAmount = movement.getAmount();
        String oldType = movement.getType();

     
        if ("CREDIT".equalsIgnoreCase(oldType)) {
            account.setInitialBalance(account.getInitialBalance().subtract(oldAmount));
        } else if ("DEBIT".equalsIgnoreCase(oldType)) {
            account.setInitialBalance(account.getInitialBalance().add(oldAmount));
        }
        
        accountRepository.save(account);
        movementRepository.delete(movement);
    }
    
}
