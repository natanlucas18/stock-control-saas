package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;

public class StockLocationFactory {

    public static StockLocation createStockLocation(Long id) {
        StockLocation stockLocation = new StockLocation();
        stockLocation.setId(id);
        stockLocation.setName("Stock Location " + id + " Test");
        stockLocation.setCompany(CompanyFactory.createCompany(1L));
        return stockLocation;
    }

    public static StockLocationDTO createStockLocationDTO(Long id) {
        return new StockLocationDTO(createStockLocation(id));
    }
}
