package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReportDTO {

    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal totalQuantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;
    private StockStatus stockStatus;

    public ProductReportDTO(Product entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.totalQuantity = entity.getTotalQuantity();
        this.stockMax = entity.getStockMax();
        this.stockMin = entity.getStockMin();
        this.unitMeasure = entity.getUnitMeasure().getAcronym();
        this.stockStatus = entity.getStockStatusEnum();
    }
}
