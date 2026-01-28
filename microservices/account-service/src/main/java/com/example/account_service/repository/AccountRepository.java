package com.example.account_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.account_service.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByNumber(String number);

}
