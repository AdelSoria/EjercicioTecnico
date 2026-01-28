package com.example.account_service.services;

import java.util.List;

import com.example.account_service.dto.AccountRequestDTO;
import com.example.account_service.dto.AccountResponseDTO;

public interface AccountService {
	
    void deleteAccount(Long id);

    List<AccountResponseDTO> listAccounts();

    AccountResponseDTO create(AccountRequestDTO dto);

    AccountResponseDTO update(Long id, AccountRequestDTO dto);
}
