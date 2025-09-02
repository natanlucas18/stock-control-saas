package com.hextech.estoque_api.application.factories;

import com.hextech.estoque_api.domain.entities.ReportPeriod;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReportPeriodFactory {

    public static ReportPeriod fromStrings(String startDate, String endDate) {
        LocalDate now = LocalDate.now();

        try {
            LocalDate end = (endDate == null || endDate.isBlank()) ? now : LocalDate.parse(endDate);
            LocalDate start = (startDate == null || startDate.isBlank()) ?
                    LocalDate.of(end.getYear(), end.getMonth().minus(1), end.getDayOfMonth()) : LocalDate.parse(startDate);

            return new ReportPeriod(start, end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Datas inválidas.");
        }
    }
}
