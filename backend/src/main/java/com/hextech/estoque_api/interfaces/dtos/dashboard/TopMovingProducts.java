package com.hextech.estoque_api.interfaces.dtos.dashboard;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.interfaces.dtos.products.ProductMinDTO;

public record TopMovingProducts(ProductMinDTO product, Long totalMovements) {

    public TopMovingProducts(Product product, Long totalMovements) {
        this(new ProductMinDTO(product), totalMovements);
    }
}
