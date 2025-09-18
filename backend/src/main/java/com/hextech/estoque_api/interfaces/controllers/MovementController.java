package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import com.hextech.estoque_api.application.services.MovementService;
import com.hextech.estoque_api.interfaces.controllers.docs.MovementControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/movements", produces = "application/json")
public class MovementController implements MovementControllerDocs {

    @Autowired
    private MovementService service;
    @Autowired
    private AuthContext auth;

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> reportMovements(
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate) {

        List<MovementResponseDTO> movements = service.getMovementsReport(startDate, endDate, auth.getCurrentCompanyId());
        return ResponseEntity.ok(movements);
    }

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO requestDTO) {
        MovementResponseDTO responseDTO = service.createAndProcessMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + responseDTO.getId());

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
