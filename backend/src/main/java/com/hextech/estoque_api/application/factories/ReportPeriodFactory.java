package com.hextech.estoque_api.application.factories;

import com.hextech.estoque_api.domain.entities.ReportPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ReportPeriodFactory {

    public static ReportPeriod fromString(String startDate, String endDate) {
        LocalDate now = LocalDate.now();

        try {
            LocalDate end = (endDate.isEmpty()) ? now : LocalDate.parse(endDate);
            LocalDate start = (startDate.isEmpty()) ?
                    LocalDate.of(end.getYear(), end.getMonth().minus(1), end.getDayOfMonth()) : LocalDate.parse(startDate);

            if (start.isAfter(end)) throw new IllegalArgumentException("Data inicial não pode ser posterior a data final.");

            return new ReportPeriod(
                    LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 23, 59)
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Datas inválidas. Use o formato yyyy-MM-dd.");
        }
    }
}
