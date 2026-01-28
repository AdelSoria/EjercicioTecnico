package com.example.account_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.account_service.dto.MovementRequestDTO;
import com.example.account_service.dto.MovementResponseDTO;
import com.example.account_service.services.MovementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {
	@Autowired
	private MovementService service;

	@PostMapping
	public ResponseEntity<MovementResponseDTO> create(@Valid @RequestBody MovementRequestDTO dto) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
	}

	@GetMapping
	public List<MovementResponseDTO> list() {
		return service.list();
	}
	
    @PutMapping("/{id}")
    public MovementResponseDTO update(
            @PathVariable Long id,
            @RequestBody MovementRequestDTO dto) {

        return service.update(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
