package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductResumeDTO {

    private Long id;
    private String code;
    private String name;
    private BigDecimal totalQuantity;
    private String unitMeasure;
    private StockStatus stockStatus;

    public ProductResumeDTO(Product entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.totalQuantity = entity.getTotalQuantity();
        this.unitMeasure = entity.getUnitMeasure().getAcronym();
        this.stockStatus = entity.getStockStatusEnum();
    }
}
