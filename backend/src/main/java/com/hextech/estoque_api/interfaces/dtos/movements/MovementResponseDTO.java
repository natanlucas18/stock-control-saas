package com.hextech.estoque_api.interfaces.dtos.movements;

import com.hextech.estoque_api.interfaces.dtos.products.ProductMinDTO;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.interfaces.dtos.users.UserMinDTO;
import com.hextech.estoque_api.domain.entities.Movement;

import java.time.LocalDate;

public class MovementResponseDTO {

    private Long id;
    private String type;
    private Integer quantity;
    private LocalDate moment;
    private String note;
    private ProductMinDTO product;
    private UserMinDTO user;
    private StockLocationDTO stockLocation;

    public MovementResponseDTO() {
    }

    public MovementResponseDTO(Movement entity) {
        this.id = entity.getId();
        this.type = entity.getType().toString();
        this.quantity = entity.getQuantity();
        this.moment = entity.getMoment();
        this.note = entity.getNote();
        this.product = new ProductMinDTO(entity.getProduct().getId(), entity.getProduct().getName());
        this.user = new UserMinDTO(entity.getUser().getId(), entity.getUser().getName());
        this.stockLocation = new StockLocationDTO(entity.getStockLocation().getId(), entity.getStockLocation().getName());
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getMoment() {
        return moment;
    }

    public void setMoment(LocalDate moment) {
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

    public StockLocationDTO getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationDTO stockLocation) {
        this.stockLocation = stockLocation;
    }
}
