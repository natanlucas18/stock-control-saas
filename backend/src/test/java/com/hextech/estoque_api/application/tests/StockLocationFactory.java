package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;

public class StockLocationFactory {

    public static StockLocation createStockLocation(Long id) {
        return new StockLocation(id, "Stock location test");
    }

    public static StockLocationDTO createStockLocationDTO(Long id) {
        return new StockLocationDTO(createStockLocation(id));
    }
}
