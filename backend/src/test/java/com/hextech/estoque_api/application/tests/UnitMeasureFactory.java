package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureRequestDTO;

public class UnitMeasureFactory {

    public static UnitMeasure createUnitMeasure(Long id) {
        Company company = CompanyFactory.createCompany(1L);
        UnitMeasure unitMeasure = UnitMeasure.createNewUnitMeasure("Kilogram", "KG", company);
        unitMeasure.setId(id);
        return unitMeasure;
    }

    public static UnitMeasureRequestDTO createUnitMeasureRequestDTO() {
        UnitMeasure unitMeasure = createUnitMeasure(1L);
        return new UnitMeasureRequestDTO(unitMeasure.getName(), unitMeasure.getAcronym());
    }
}
