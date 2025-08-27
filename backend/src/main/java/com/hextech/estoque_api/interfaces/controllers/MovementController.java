package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.application.dtos.movements.MovementResponseDTO;
import com.hextech.estoque_api.application.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/movements", produces = "application/json")
public class MovementController {

    @Autowired
    private MovementService service;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO requestDTO) {
        MovementResponseDTO responseDTO = service.createAndProcessMovement(requestDTO);

        URI uri = URI.create("/api/movements/" + responseDTO.getId());

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
