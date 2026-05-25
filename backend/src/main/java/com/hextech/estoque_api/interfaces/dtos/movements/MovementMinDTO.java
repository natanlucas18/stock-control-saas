package com.hextech.estoque_api.interfaces.dtos.movements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.interfaces.dtos.products.ProductMinDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovementMinDTO(
        Long id,
        LocalDateTime moment,
        String type,
        BigDecimal quantity,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        ProductMinDTO product
) {
        public MovementMinDTO(Movement entity) {
                this(entity.getId(), entity.getMoment(), entity.getType().toString(), entity.getQuantity(),
                        new ProductMinDTO(entity.getProduct()));
        }
}
