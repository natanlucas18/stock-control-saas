package com.hextech.estoque_api.interfaces.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;
}
