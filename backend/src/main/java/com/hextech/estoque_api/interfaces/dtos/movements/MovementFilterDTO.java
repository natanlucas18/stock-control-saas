package com.hextech.estoque_api.interfaces.dtos.movements;

import com.hextech.estoque_api.domain.entities.movement.MovementType;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovementFilterDTO {

    @PastOrPresent(message = "Data inicial não pode ser futura.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Data final não pode ser futura.")
    private LocalDate endDate;
    private MovementType type;
    private Long productId;
}
