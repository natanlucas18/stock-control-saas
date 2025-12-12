package com.hextech.estoque_api.domain.entities.product;

public enum StockStatus {
        LOW,
        NORMAL,
        HIGH;

    public static StockStatus checkStockStatus(String status) {
        try {
            return StockStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de status inválido.");
        }
    }
}
