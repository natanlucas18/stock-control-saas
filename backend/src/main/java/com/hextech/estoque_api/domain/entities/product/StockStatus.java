package com.hextech.estoque_api.domain.entities.product;

public record StockStatus(Level level, String message) {

    public StockStatus(Level level) {
        this(level, level.getMessage());
    }

    public enum Level {
        LOW("Estoque abaixo do valor mínimo definido."),
        NORMAL("Estoque entre os valores definidos."),
        HIGH("Estoque acima do valor máximo definido.");

        private final String message;

        Level(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
