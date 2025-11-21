package com.hextech.estoque_api.interfaces.dtos.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Código do produto não pode ser nulo ou vazio.")
    @Size(min = 1, max = 10, message = "Código do produto deve ter no máximo 10 caracteres.")
    private String code;
    @NotBlank(message = "Nome do produto não pode ser nulo ou vazio.")
    @Size(min = 2, message = "Nome do produto deve ter no mínimo 2 caracteres.")
    private String name;
    @NotNull(message = "Preço do produto não pode ser nulo.")
    private BigDecimal price;
    @NotNull(message = "Estoque máximo do produto não pode ser nulo.")
    private BigDecimal stockMax;
    @NotNull(message = "Estoque mínimo do produto não pode ser nulo.")
    private BigDecimal stockMin;
    @NotBlank(message = "Tipo de unidade de medida do produto não pode ser nulo ou vazio.")
    @Size(min = 2, max = 2, message = "Tipo de unidade de medida do produto deve ter 2 caracteres.")
    private String unitMeasure;
}
