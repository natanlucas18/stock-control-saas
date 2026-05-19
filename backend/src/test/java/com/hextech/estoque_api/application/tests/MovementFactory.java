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
        StockLocation toStockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.ENTRADA, new BigDecimal(10), LocalDateTime.now(), "Entrada de teste", product, user, company, null, toStockLocation);
    }

    public static Movement createExitMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation fromStockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.SAIDA, new BigDecimal(5), LocalDateTime.now(), "Saída de teste", product, user, company, fromStockLocation, null);
    }

    public static Movement createTransferMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation fromStockLocation = StockLocationFactory.createStockLocation(1L);
        StockLocation toStockLocation = StockLocationFactory.createStockLocation(2L);
        return Movement.createNewMovement(MovementType.TRANSFERENCIA, new BigDecimal(5), LocalDateTime.now(), "Transferência de teste", product, user, company, fromStockLocation, toStockLocation);
    }

    public static Movement createReturnMovement() {
        Product product = ProductFactory.createProduct(1L);
        User user = UserFactory.createUser(1L);
        Company company = CompanyFactory.createCompany(1L);
        StockLocation toStockLocation = StockLocationFactory.createStockLocation(1L);
        return Movement.createNewMovement(MovementType.DEVOLUCAO, new BigDecimal(10), LocalDateTime.now(), "Devolução de teste", product, user, company, null, toStockLocation);
    }

    public static MovementRequestDTO createMovementRequestDTO(String type, BigDecimal quantity, String note, Long productId, Long fromStockLocationId, Long toStockLocationId) {
        MovementRequestDTO dto = new MovementRequestDTO();
        dto.setType(type);
        dto.setQuantity(quantity);
        dto.setNote(note);
        dto.setProductId(productId);
        dto.setFromStockLocationId(fromStockLocationId);
        dto.setToStockLocationId(toStockLocationId);
        return dto;
    }
}
