package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.*;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;

import java.time.LocalDate;

public class MovementFactory {

    public static Movement createEntryMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation stockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.ENTRADA, 10, LocalDate.now(), "Entrada de teste", product, user, company, stockLocation);
    }

    public static Movement createExitMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation stockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.SAIDA, 5, LocalDate.now(), "Saída de teste", product, user, company, stockLocation);
    }

    public static MovementRequestDTO createMovementRequestDTO() {
        MovementRequestDTO dto = new MovementRequestDTO();
        dto.setType("ENTRADA");
        dto.setQuantity(10);
        dto.setNote("Entrada de teste");
        dto.setProductId(1L);
        dto.setStockLocationId(1L);
        return dto;
    }
}
