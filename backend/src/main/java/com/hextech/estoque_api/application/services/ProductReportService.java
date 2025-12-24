package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductReportService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductReportDTO> getProductsReport(String status, Long companyId, Pageable pageable) {

        String parsedStatus = (status == null || status.isBlank()) ? null : StockStatus.checkStockStatus(status).name();

        Page<Product> productsPage = repository.searchProductsReport(parsedStatus, companyId, pageable);

        return productsPage.map(ProductReportDTO::new);
    }
}
