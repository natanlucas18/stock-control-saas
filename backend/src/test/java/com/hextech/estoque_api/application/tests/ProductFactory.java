package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.Product;
import com.hextech.estoque_api.domain.entities.UnitMeasure;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;

public class ProductFactory {

    public static Product createProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setQuantity(50);
        product.setUnitMeasure(UnitMeasure.UN);
        product.setStockLocation(StockLocationFactory.createStockLocation(1L));
        product.setCompany(CompanyFactory.createCompany(1L));
        return product;
    }

    public static ProductRequestDTO createProductRequestDTO() {
        Product product = createProduct(1L);
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setUnitMeasure(product.getUnitMeasure().toString());
        dto.setStockLocationId(product.getStockLocation().getId());
        return dto;
    }
}
