package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductFilterDTO;
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
    public Page<ProductReportDTO> getProductsReport(ProductFilterDTO filter, Long companyId, Pageable pageable) {

        Page<Product> productsPage = repository.searchAllProducts(filter.getProductId(), companyId, pageable);

        if (filter.getStatus() != null) {

            StockStatus status;
            try {
                status = StockStatus.valueOf(filter.getStatus());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido.");
            }

            List<Product> filteredProducts = productsPage.filter(product -> product.checkStockStatus().equals(status)).stream().toList();
            List<ProductReportDTO> products = filteredProducts.stream().map(ProductReportDTO::new).toList();
            return new PageImpl<>(products, productsPage.getPageable(), products.size());
        }
        return productsPage.map(ProductReportDTO::new);
    }
}
