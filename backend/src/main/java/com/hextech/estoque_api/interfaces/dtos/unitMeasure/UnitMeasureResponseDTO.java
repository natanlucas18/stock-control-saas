package com.hextech.estoque_api.interfaces.dtos.unitMeasure;

import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;

public record UnitMeasureResponseDTO(Long id, String name, String acronym) {

    public UnitMeasureResponseDTO(UnitMeasure entity) {
        this(entity.getId(), entity.getName(), entity.getAcronym());
    }
}
