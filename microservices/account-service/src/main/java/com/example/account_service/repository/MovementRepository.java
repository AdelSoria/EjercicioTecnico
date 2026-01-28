package com.example.account_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.account_service.domain.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {
	@Query("""
		    SELECT m FROM Movement m 
		    JOIN FETCH m.account a 
		    WHERE a.customerId = :clientId 
		      AND m.date BETWEEN :start AND :end 
		    ORDER BY m.date ASC
		""")
		List<Movement> findByClientIdAndDateBetween(
		        @Param("clientId") Long clientId,
		        @Param("start") LocalDateTime start,
		        @Param("end") LocalDateTime end
		);
}
