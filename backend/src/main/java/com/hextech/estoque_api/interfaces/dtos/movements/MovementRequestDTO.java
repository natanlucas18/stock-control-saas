package com.hextech.estoque_api.interfaces.dtos.movements;

public class MovementRequestDTO {

    private String type;
    private Integer quantity;
    private String note;
    private Long productId;
    private Long stockLocationId;

    public MovementRequestDTO() {
    }

    public MovementRequestDTO(String type, Integer quantity, String note, Long productId, Long stockLocationId) {
        this.type = type;
        this.quantity = quantity;
        this.note = note;
        this.productId = productId;
        this.stockLocationId = stockLocationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public Long getStockLocationId() {
        return stockLocationId;
    }

    public void setStockLocationId(Long stockLocationId) {
        this.stockLocationId = stockLocationId;
    }
}
