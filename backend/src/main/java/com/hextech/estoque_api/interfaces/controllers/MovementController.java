package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.MovementService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.MovementControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/movements", produces = "application/json")
public class MovementController implements MovementControllerDocs {

    @Autowired
    private MovementService service;
    @Autowired
    private AuthContext auth;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> reportMovements(
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            Pageable pageable
            ) {
        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        Pageable adjustedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        Page<MovementResponseDTO> response = service.getMovementsReport(startDate, endDate, auth.getCurrentCompanyId(), adjustedPageable);

        List<MovementResponseDTO> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<MovementResponseDTO> paginatedResponse = new PaginatedResponse<MovementResponseDTO>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }

    @PostMapping
    public ResponseEntity<StandardResponse<?>> createMovement(@RequestBody MovementRequestDTO requestDTO) {
        MovementResponseDTO response = service.createAndProcessMovement(requestDTO, auth.getCurrentCompanyId(), auth.getCurrentUserId());

        URI uri = URI.create("/api/movements/" + response.getId());

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }
}
