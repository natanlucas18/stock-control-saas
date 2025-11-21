package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.MovementService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.MovementControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/movements", produces = "application/json")
public class MovementController implements MovementControllerDocs {

    @Autowired
    private MovementService service;
    @Autowired
    private AuthContext auth;

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<?>> findById(@PathVariable Long id) {
        MovementResponseDTO response = service.findById(id);
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @PostMapping(value = "/entry")
    public ResponseEntity<StandardResponse<?>> createEntryMovement(@RequestBody @Valid MovementRequestDTO requestDTO) {
        MovementResponseDTO response = service.createEntryMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + response.getId());

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PostMapping("/exit")
    public ResponseEntity<StandardResponse<?>> createExitMovement(@RequestBody @Valid MovementRequestDTO requestDTO) {
        MovementResponseDTO response = service.createExitMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + response.getId());

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PostMapping("/transfer")
    public ResponseEntity<StandardResponse<?>> createTransferMovement(@RequestBody @Valid MovementRequestDTO requestDTO) {
        MovementResponseDTO response = service.createTransferMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + response.getId());

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PostMapping("/return")
    public ResponseEntity<StandardResponse<?>> createReturnMovement(@RequestBody @Valid MovementRequestDTO requestDTO) {
        MovementResponseDTO response = service.createReturnMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + response.getId());

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }
}
