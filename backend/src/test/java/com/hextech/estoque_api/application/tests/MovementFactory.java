package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovementFactory {

    public static Movement createEntryMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation stockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.ENTRADA, new BigDecimal(10), LocalDateTime.now(), "Entrada de teste", product, user, company, stockLocation);
    }

    public static Movement createExitMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation stockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.SAIDA, new BigDecimal(5), LocalDateTime.now(), "Saída de teste", product, user, company, stockLocation);
    }

    public static MovementRequestDTO createMovementRequestDTO() {
        MovementRequestDTO dto = new MovementRequestDTO();
        dto.setType("ENTRADA");
        dto.setQuantity(new BigDecimal(10));
        dto.setNote("Entrada de teste");
        dto.setProductId(1L);
        dto.setStockLocationId(1L);
        return dto;
    }
}
