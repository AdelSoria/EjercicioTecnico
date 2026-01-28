package com.example.account_service.services;

import java.util.List;

import com.example.account_service.dto.MovementRequestDTO;
import com.example.account_service.dto.MovementResponseDTO;

public interface MovementService {

    MovementResponseDTO create(MovementRequestDTO dto);

    List<MovementResponseDTO> list();

    MovementResponseDTO update(Long id, MovementRequestDTO dto);

    void delete(Long id);
	
}
