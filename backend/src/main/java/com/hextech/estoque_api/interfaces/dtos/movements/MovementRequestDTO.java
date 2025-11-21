package com.hextech.estoque_api.interfaces.dtos.movements;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDTO {

    @NotBlank(message = "Tipo de movimentação não pode ser nulo ou vazio.")
    private String type;
    @NotNull(message = "Quantidade não pode ser nula.")
    @DecimalMin(value = "0.001", message = "Quantidade deve ser maior que zero.")
    private BigDecimal quantity;
    private String note;
    @NotNull(message = "Produto não pode ser nulo.")
    private Long productId;
    private Long fromStockLocationId;
    private Long toStockLocationId;
}
