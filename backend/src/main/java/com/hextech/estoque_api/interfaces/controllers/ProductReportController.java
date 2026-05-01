package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.PdfService;
import com.hextech.estoque_api.application.services.ProductReportService;
import com.hextech.estoque_api.infrastructure.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/reports/products", produces = "application/json")
public class ProductReportController {

    @Autowired
    private AuthContext auth;
    @Autowired
    private ProductReportService productReportService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> reportProducts(
            @RequestParam(value = "status", defaultValue = "") String status, Pageable pageable) {
        Page<ProductReportDTO> response = productReportService.getProductsReport(status, auth.getCurrentCompanyId(), pageable);

        List<ProductReportDTO> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<?> paginatedResponse = new PaginatedResponse<>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }

    @GetMapping(produces = "application/pdf")
    public ResponseEntity<byte[]> reportProductsPdf(
            @RequestParam(value = "status", defaultValue = "") String status, Pageable pageable) {
        List<ProductReportDTO> response = productReportService.getProductsReportToPdf(status, auth.getCurrentCompanyId(), pageable);

        byte[] pdf = pdfService.createPdf("product-report", Map.of("products", response));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=produtos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
