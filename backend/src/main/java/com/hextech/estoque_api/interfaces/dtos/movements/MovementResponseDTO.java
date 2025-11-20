package com.hextech.estoque_api.interfaces.dtos.movements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.interfaces.dtos.products.ProductMinDTO;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.interfaces.dtos.users.UserMinDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovementResponseDTO {

    private Long id;
    private String type;
    private BigDecimal quantity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moment;
    private String note;
    private ProductMinDTO product;
    private UserMinDTO user;
    private StockLocationDTO fromStockLocation;
    private StockLocationDTO toStockLocation;

    public MovementResponseDTO() {
    }

    public MovementResponseDTO(Movement entity) {
        this.id = entity.getId();
        this.type = entity.getType().toString();
        this.quantity = entity.getQuantity();
        this.moment = entity.getMoment();
        this.note = entity.getNote();
        this.product = new ProductMinDTO(entity.getProduct());
        this.user = new UserMinDTO(entity.getUser().getId(), entity.getUser().getName());
        this.fromStockLocation = (entity.getFromStockLocation() == null) ? null : new StockLocationDTO(entity.getFromStockLocation().getId(), entity.getFromStockLocation().getName());
        this.toStockLocation = (entity.getToStockLocation() == null) ? null : new StockLocationDTO(entity.getToStockLocation().getId(), entity.getToStockLocation().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ProductMinDTO getProduct() {
        return product;
    }

    public void setProduct(ProductMinDTO product) {
        this.product = product;
    }

    public UserMinDTO getUser() {
        return user;
    }

    public void setUser(UserMinDTO user) {
        this.user = user;
    }

    public StockLocationDTO getFromStockLocation() {
        return fromStockLocation;
    }

    public void setFromStockLocation(StockLocationDTO fromStockLocation) {
        this.fromStockLocation = fromStockLocation;
    }

    public StockLocationDTO getToStockLocation() {
        return toStockLocation;
    }

    public void setToStockLocation(StockLocationDTO toStockLocation) {
        this.toStockLocation = toStockLocation;
    }
}
