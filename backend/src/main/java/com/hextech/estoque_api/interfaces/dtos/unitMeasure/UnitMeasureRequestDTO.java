package com.hextech.estoque_api.interfaces.dtos.unitMeasure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitMeasureRequestDTO(@NotBlank(message = "Nome da U.M. não pode ser nulo ou vazio.")
                                    @Size(min = 2, max = 15, message = "Nome da U.M. deve ter entre 2 e 15 caracteres.")
                                    String name,
                                    @NotBlank(message = "Sigla da U.M. não pode ser nulo ou vazio.")
                                    @Size(min = 1, max = 2, message = "Sigla da U.M. deve ter entre 1 e 2 caracteres.")
                                    String acronym) {
    public UnitMeasureRequestDTO {
        name = name.toUpperCase();
        acronym = acronym.toUpperCase();
    }
}
