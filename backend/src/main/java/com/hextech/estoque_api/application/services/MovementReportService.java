package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.factories.ReportPeriodFactory;
import com.hextech.estoque_api.domain.entities.ReportPeriod;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.movement.MovementType;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.infrastructure.utils.PageableUtils;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hextech.estoque_api.application.services.ProductService.parseProductId;

@Service
public class MovementReportService {

    @Autowired
    private MovementRepository repository;

    public Page<MovementResponseDTO> getMovementsReport(String startDate, String endDate, String type, String productId, Long companyId, Pageable pageable) {
        ReportPeriod period = ReportPeriodFactory.fromString(startDate, endDate);
        Long productIdParsed = parseProductId(productId);
        MovementType checkedType = (type.isEmpty() || type.equals("null")) ? null : MovementType.checkMovementType(type);
        Pageable validPageable = PageableUtils.validatePageable(pageable, List.of("id", "type", "moment"));

        Page<Movement> movements = repository.searchAllMovements(period.getStartDate(), period.getEndDate(), checkedType,
                productIdParsed, companyId, validPageable);
        return movements.map(MovementResponseDTO::new);
    }
}
