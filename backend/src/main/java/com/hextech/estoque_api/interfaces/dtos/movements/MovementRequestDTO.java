package com.hextech.estoque_api.interfaces.dtos.movements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDTO {

    private String type;
    private BigDecimal quantity;
    private String note;
    private Long productId;
    private Long fromStockLocationId;
    private Long toStockLocationId;
}
