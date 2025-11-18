package com.hextech.estoque_api.application.factories;

import com.hextech.estoque_api.domain.entities.ReportPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

public class ReportPeriodFactory {

    public static ReportPeriod fromDates(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();

        try {
            LocalDate end = (endDate == null) ? now : endDate;
            LocalDate start = (startDate == null) ?
                    LocalDate.of(end.getYear(), end.getMonth().minus(1), end.getDayOfMonth()) : startDate;

            return new ReportPeriod(
                    LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 23, 59)
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Datas inválidas.");
        }
    }
}
