package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.*;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import com.hextech.estoque_api.infrastructure.repositories.*;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private StockMovementDomainService domainService;

    @Transactional
    public MovementResponseDTO createAndProcessMovement(MovementRequestDTO requestDTO, Long currentCompanyId, Long currentUserId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(currentUserId, company.getId())
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
}
