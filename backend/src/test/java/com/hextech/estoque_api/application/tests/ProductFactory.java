package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;

import java.math.BigDecimal;

public class ProductFactory {

    public static Product createProduct(Long id) {
        Company company = CompanyFactory.createCompany(1L);
        return Product.createNewProduct("PRODTEST" + id, "Test Product " + id,
                new BigDecimal("100.00"), new BigDecimal(100), new BigDecimal(10),
                UnitMeasure.createNewUnitMeasure("UNIDADE", "UN", company),
                company);
    }

    public static ProductRequestDTO createProductRequestDTO() {
        Product product = createProduct(1L);
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockMax(product.getStockMax());
        dto.setStockMin(product.getStockMin());
        dto.setUnitMeasureId(1L);
        return dto;
    }
}
