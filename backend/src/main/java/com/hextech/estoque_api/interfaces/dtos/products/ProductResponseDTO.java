package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockQuantityDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal totalQuantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;
    private StockStatus stockStatus;
    private Set<StockQuantityDTO> stockLocations;

    public ProductResponseDTO(Product entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.totalQuantity = entity.getTotalQuantity();
        this.stockMax = entity.getStockMax();
        this.stockMin = entity.getStockMin();
        this.unitMeasure = entity.getUnitMeasure().getAcronym();
        this.stockLocations = entity.getStocks().stream().map(
                stockProduct -> new StockQuantityDTO(stockProduct.getStockLocation(), stockProduct.getQuantity())).collect(Collectors.toSet());
        this.stockStatus = entity.getStockStatusEnum();
    }
}
