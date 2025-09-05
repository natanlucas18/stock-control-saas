package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.application.dtos.movements.MovementResponseDTO;
import com.hextech.estoque_api.application.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.factories.ReportPeriodFactory;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.*;
import com.hextech.estoque_api.domain.repositories.*;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovementService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockLocationRepository stockLocationRepository;
    @Autowired
    private MovementRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private StockMovementDomainService domainService;

    @Transactional
    public MovementResponseDTO createAndProcessMovement(MovementRequestDTO requestDTO) {
        Company company = companyRepository.findById(authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(authContext.getCurrentUserId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation stockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndCompanyId(requestDTO.getProductId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        MovementType type;
        try {
             type = MovementType.valueOf(requestDTO.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        Movement entity = domainService.processMovement(type, requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, company, stockLocation);

        productRepository.save(product);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<MovementResponseDTO> getMovementsReport(String startDate, String endDate, Long companyId) {
        ReportPeriod period = ReportPeriodFactory.fromStrings(startDate, endDate);

        List<Movement> movements = repository.searchAllByCompanyIdAndDate(companyId, period.getStartDate(), period.getEndDate());
        return movements.stream().map(MovementResponseDTO::new).toList();
    }
}
