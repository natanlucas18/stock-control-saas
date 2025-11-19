package com.hextech.estoque_api.domain.services;

import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.InsufficientQuantityException;
import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class StockMovementDomainService {

    public Movement processEntryMovement(String type, BigDecimal quantity, String note,
                                         Product product, User user, Company company, StockLocation toStockLocation, StockProduct stockProduct) {
        MovementType checkedType = checkMovementType(type);
        if (checkedType.equals(MovementType.ENTRADA)) {
            increaseQuantity(quantity, stockProduct);
        } else {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        return Movement.createNewMovement(checkedType, quantity, LocalDateTime.now(ZoneId.systemDefault()), note, product, user, company, null, toStockLocation);
    }

    public Movement processExitMovement(String type, BigDecimal quantity, String note,
                                         Product product, User user, Company company, StockLocation fromStockLocation, StockProduct stockProduct) {
        MovementType checkedType = checkMovementType(type);
        if (checkedType.equals(MovementType.SAIDA)) {
            decreaseQuantity(quantity, stockProduct);
        } else {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        return Movement.createNewMovement(checkedType, quantity, LocalDateTime.now(ZoneId.systemDefault()), note, product, user, company, fromStockLocation, null);
    }

    public Movement processTransferMovement(String type, BigDecimal quantity, String note,
                                         Product product, User user, Company company, StockLocation fromStockLocation,
                                            StockLocation toStockLocation, StockProduct fromStockProduct, StockProduct toStockProduct) {
        MovementType checkedType = checkMovementType(type);
        if (checkedType.equals(MovementType.TRANSFERENCIA)) {
            decreaseQuantity(quantity, fromStockProduct);
            increaseQuantity(quantity, toStockProduct);
        } else {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        return Movement.createNewMovement(checkedType, quantity, LocalDateTime.now(ZoneId.systemDefault()), note, product, user, company, fromStockLocation, toStockLocation);
    }

    public Movement processReturnMovement(String type, BigDecimal quantity, String note,
                                         Product product, User user, Company company, StockLocation toStockLocation, StockProduct stockProduct) {
        MovementType checkedType = checkMovementType(type);
        if (checkedType.equals(MovementType.DEVOLUCAO)) {
            increaseQuantity(quantity, stockProduct);
        } else {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        return Movement.createNewMovement(checkedType, quantity, LocalDateTime.now(ZoneId.systemDefault()), note, product, user, company, null, toStockLocation);
    }

    private void increaseQuantity(BigDecimal quantity, StockProduct stockProduct) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        stockProduct.setQuantity(stockProduct.getQuantity().add(quantity));
    }

    private void decreaseQuantity(BigDecimal quantity, StockProduct stockProduct) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        if (stockProduct.getQuantity().compareTo(quantity) < 0) throw new InsufficientQuantityException("Quantidade insuficiente em estoque.");
        stockProduct.setQuantity(stockProduct.getQuantity().subtract(quantity));
    }

    private MovementType checkMovementType(String type) {
        MovementType validType;
        try {
            validType = MovementType.valueOf(type);
            return validType;
        } catch (IllegalArgumentException e) {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }
    }
}
