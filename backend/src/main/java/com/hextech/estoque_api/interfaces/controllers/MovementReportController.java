package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.MovementReportService;
import com.hextech.estoque_api.infrastructure.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.MovementReportControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports/movements", produces = "application/json")
public class MovementReportController implements MovementReportControllerDocs {

    @Autowired
    private AuthContext auth;
    @Autowired
    private MovementReportService movementReportService;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> reportMovements(
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate", defaultValue = "") String endDate,
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "productId", defaultValue = "") String productId,
            Pageable pageable
    ) {

        Page<MovementResponseDTO> response = movementReportService.getMovementsReport(startDate, endDate, type,
                productId, auth.getCurrentCompanyId(), pageable);

        List<MovementResponseDTO> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<MovementResponseDTO> paginatedResponse = new PaginatedResponse<>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }
}
