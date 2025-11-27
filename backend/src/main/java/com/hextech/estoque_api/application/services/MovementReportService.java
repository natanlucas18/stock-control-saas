package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.factories.ReportPeriodFactory;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.ReportPeriod;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementFilterDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovementReportService {

    @Autowired
    private MovementRepository repository;

    public Page<MovementResponseDTO> getMovementsReport(MovementFilterDTO filter, Long companyId, Pageable pageable) {
        ReportPeriod period = ReportPeriodFactory.fromDates(filter.getStartDate(), filter.getEndDate());

        Page<Movement> movements = repository.searchAllMovements(period.getStartDate(), period.getEndDate(), filter.getType(),
                filter.getProductId(), companyId, pageable);
        return movements.map(MovementResponseDTO::new);
    }
}
