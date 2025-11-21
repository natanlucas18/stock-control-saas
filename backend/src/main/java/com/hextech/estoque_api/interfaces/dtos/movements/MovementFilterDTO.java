package com.hextech.estoque_api.interfaces.dtos.movements;

import com.hextech.estoque_api.domain.entities.movement.MovementType;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovementFilterDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private MovementType type;
    @Positive
    private Long productId;
}
