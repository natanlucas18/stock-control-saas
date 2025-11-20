package com.hextech.estoque_api.interfaces.dtos.movements;

import java.math.BigDecimal;

public class MovementRequestDTO {

    private String type;
    private BigDecimal quantity;
    private String note;
    private Long productId;
    private Long fromStockLocationId;
    private Long toStockLocationId;

    public MovementRequestDTO() {
    }

    public MovementRequestDTO(String type, BigDecimal quantity, String note, Long productId, Long fromStockLocationId, Long toStockLocationId) {
        this.type = type;
        this.quantity = quantity;
        this.note = note;
        this.productId = productId;
        this.fromStockLocationId = fromStockLocationId;
        this.toStockLocationId = toStockLocationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getFromStockLocationId() {
        return fromStockLocationId;
    }

    public void setFromStockLocationId(Long fromStockLocationId) {
        this.fromStockLocationId = fromStockLocationId;
    }

    public Long getToStockLocationId() {
        return toStockLocationId;
    }

    public void setToStockLocationId(Long toStockLocationId) {
        this.toStockLocationId = toStockLocationId;
    }
}
