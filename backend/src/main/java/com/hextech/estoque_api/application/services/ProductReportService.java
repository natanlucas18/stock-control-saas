package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductReportService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductReportDTO> getProductsReport(String status, Long companyId, Pageable pageable) {

        Page<Product> productsPage = repository.searchAllProducts(companyId, pageable);

        if (!status.isBlank()) {
            StockStatus validStatus = StockStatus.checkStockStatus(status);

            List<Product> filteredProducts = productsPage.filter(product -> product.checkStockStatus().equals(validStatus)).stream().toList();
            List<ProductReportDTO> products = filteredProducts.stream().map(ProductReportDTO::new).toList();
            return new PageImpl<>(products, productsPage.getPageable(), products.size());
        }
        return productsPage.map(ProductReportDTO::new);
    }
}
