package com.hextech.estoque_api.domain.entities.product;

import lombok.Getter;

@Getter
public enum StockStatus {
        LOW("BAIXO"),
        NORMAL("NORMAL"),
        HIGH("ALTO");

        private final String description;

        StockStatus(String description) {
            this.description = description;
        }

    public static StockStatus checkStockStatus(String status) {
        try {
            return StockStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de status inválido.");
        }
    }
}
