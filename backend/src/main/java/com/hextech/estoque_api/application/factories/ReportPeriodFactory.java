package com.hextech.estoque_api.application.factories;

import com.hextech.estoque_api.domain.entities.ReportPeriod;
import com.hextech.estoque_api.domain.exceptions.BusinessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ReportPeriodFactory {

    public static ReportPeriod fromString(String startDate, String endDate) {
        LocalDate now = LocalDate.now();

        try {
            LocalDate end = (endDate.isEmpty() || endDate.equals("null")) ? now : LocalDate.parse(endDate);
            LocalDate start = getLocalDate(startDate, end, now);

            return new ReportPeriod(
                    LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 23, 59)
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Datas inválidas. Use o formato yyyy-MM-dd.");
        }
    }

    private static LocalDate getLocalDate(String startDate, LocalDate end, LocalDate now) {
        LocalDate start = (startDate.isEmpty() || startDate.equals("null")) ?
                end.minusMonths(1) : LocalDate.parse(startDate);

        if (start.isAfter(end)) throw new BusinessException("Data inicial não pode ser posterior a data final.");

        if (start.isAfter(now) || end.isAfter(now)) throw new BusinessException("Datas não podem ser maiores que a data atual.");

        if (start.plusYears(1).isBefore(end)) throw new BusinessException("Período não pode ser maior que 1 ano.");
        return start;
    }
}
