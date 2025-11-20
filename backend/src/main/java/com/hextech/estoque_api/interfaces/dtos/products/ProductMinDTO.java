package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.UnitMeasure;

public record ProductMinDTO(Long id, String code, String name, UnitMeasure unitMeasure) {

    public ProductMinDTO(Product entity) {
        this(entity.getId(), entity.getCode(), entity.getName(), entity.getUnitMeasure());
    }
}
