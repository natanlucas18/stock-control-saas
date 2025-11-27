package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.ProductReportService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.products.ProductFilterDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/reports/products", produces = "application/json")
public class ProductReportController {

    @Autowired
    private AuthContext auth;
    @Autowired
    private ProductReportService productReportService;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> reportProducts(
            @ModelAttribute @Valid ProductFilterDTO filter,
            Pageable pageable
    ) {
        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        Pageable adjustedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        Page<ProductReportDTO> response = productReportService.getProductsReport(filter, auth.getCurrentCompanyId(), adjustedPageable);

        List<ProductReportDTO> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<?> paginatedResponse = new PaginatedResponse<>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }
}
