package com.hextech.estoque_api.domain.entities.movement;

import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;

public enum MovementType {
    ENTRADA,
    SAIDA,
    TRANSFERENCIA,
    DEVOLUCAO;

    public static MovementType checkMovementType(String type) {
        MovementType validType;
        try {
            validType = MovementType.valueOf(type);
            return validType;
        } catch (IllegalArgumentException e) {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }
    }
}
