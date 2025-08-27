package com.hextech.estoque_api.domain.services;

import com.hextech.estoque_api.domain.entities.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StockMovementDomainService {

    public Movement processMovement(MovementType type, Integer quantity, String note,
                                    Product product, User user, Client client, StockLocation stockLocation) {
        if (type.equals(MovementType.ENTRADA)) {
            product.increaseQuantity(quantity);
        } else if (type.equals(MovementType.SAIDA)) {
            product.decreaseQuantity(quantity);
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválida.");
        }

        return new Movement(type, quantity, LocalDate.now(), note, product, user, client, stockLocation);
    }
}
