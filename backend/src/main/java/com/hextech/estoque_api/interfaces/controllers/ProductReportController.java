package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.ProductReportService;
import com.hextech.estoque_api.infrastructure.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
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
@RequestMapping(value = "/api/reports/products", produces = "application/json")
public class ProductReportController {

    @Autowired
    private AuthContext auth;
    @Autowired
    private ProductReportService productReportService;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> reportProducts(
            @RequestParam(value = "status", defaultValue = "") String status, Pageable pageable) {
        Page<ProductReportDTO> response = productReportService.getProductsReport(status, auth.getCurrentCompanyId(), pageable);

        List<ProductReportDTO> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<?> paginatedResponse = new PaginatedResponse<>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }
}
