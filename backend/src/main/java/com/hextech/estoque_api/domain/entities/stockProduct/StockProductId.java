package com.hextech.estoque_api.domain.entities.stockProduct;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockProductId {

    private Long stockLocationId;
    private Long productId;
}
