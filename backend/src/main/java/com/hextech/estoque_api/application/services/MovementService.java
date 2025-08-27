package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.application.dtos.movements.MovementResponseDTO;
import com.hextech.estoque_api.application.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.*;
import com.hextech.estoque_api.domain.repositories.*;
import com.hextech.estoque_api.domain.services.StockMovementDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovementService {

    @Autowired
    private ClientRepository clientRepository;
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
        Client client = clientRepository.findById(authContext.getCurrentClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        User user = userRepository.findByIdAndClientId(authContext.getCurrentUserId(), client.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation stockLocation = stockLocationRepository.findByIdAndClientId(requestDTO.getStockLocationId(), client.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndClientId(requestDTO.getProductId(), client.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        MovementType type;
        try {
             type = MovementType.valueOf(requestDTO.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidMovementTypeException("Tipo de movimentação inválida.");
        }

        Movement entity = domainService.processMovement(type, requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, client, stockLocation);

        productRepository.save(product);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }
}
