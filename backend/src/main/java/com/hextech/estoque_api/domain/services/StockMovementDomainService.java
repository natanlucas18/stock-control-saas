package com.hextech.estoque_api.domain.services;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class StockMovementDomainService {

    public Movement processMovement(MovementType type, BigDecimal quantity, String note,
                                    Product product, User user, Company company, StockLocation stockLocation) {
        if (type.equals(MovementType.ENTRADA)) {
            product.increaseQuantity(quantity);
        } else if (type.equals(MovementType.SAIDA)) {
            product.decreaseQuantity(quantity);
        } else {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        return Movement.createNewMovement(type, quantity, LocalDateTime.now(ZoneId.systemDefault()), note, product, user, company, stockLocation);
    }
}
