package com.hextech.estoque_api.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReportPeriod {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ReportPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de fim.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
