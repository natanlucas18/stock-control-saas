package com.hextech.estoque_api.interfaces.dtos.movements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.interfaces.dtos.products.ProductMinDTO;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.interfaces.dtos.users.UserMinDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public MovementResponseDTO(Movement entity) {
        this.id = entity.getId();
        this.type = entity.getType().toString();
        this.quantity = entity.getQuantity();
        this.moment = entity.getMoment();
        this.note = entity.getNote();
        this.product = new ProductMinDTO(entity.getProduct());
        this.user = new UserMinDTO(entity.getUser().getId(), entity.getUser().getName());
        this.fromStockLocation = (entity.getFromStockLocation() == null) ?
                null : new StockLocationDTO(entity.getFromStockLocation().getId(), entity.getFromStockLocation().getName());
        this.toStockLocation = (entity.getToStockLocation() == null) ?
                null : new StockLocationDTO(entity.getToStockLocation().getId(), entity.getToStockLocation().getName());
    }
}
