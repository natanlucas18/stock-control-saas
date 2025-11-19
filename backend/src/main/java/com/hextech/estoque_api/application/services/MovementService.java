package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.stockProduct.StockProduct;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
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
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockProductRepository stockProductRepository;
    @Autowired
    private StockMovementDomainService domainService;

    public MovementResponseDTO findById(Long id) {
        Movement entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação não encontrada."));
        return new MovementResponseDTO(entity);
    }

    @Transactional
    public MovementResponseDTO createEntryMovement(MovementRequestDTO requestDTO, Long currentCompanyId, Long currentUserId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(currentUserId, company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation toStockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getToStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndCompanyId(requestDTO.getProductId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        StockProduct stockProduct = stockProductRepository.findByStockLocationIdAndProductId(toStockLocation.getId(), product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no local de estoque."));

        Movement entity = domainService.processEntryMovement(requestDTO.getType(), requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, company, toStockLocation, stockProduct);

        productService.updateTotalQuantity(product.getId());

        stockProductRepository.save(stockProduct);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }

    @Transactional
    public MovementResponseDTO createExitMovement(MovementRequestDTO requestDTO, Long currentCompanyId, Long currentUserId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(currentUserId, company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation fromStockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getFromStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndCompanyId(requestDTO.getProductId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        StockProduct stockProduct = stockProductRepository.findByStockLocationIdAndProductId(fromStockLocation.getId(), product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no local de estoque."));

        Movement entity = domainService.processExitMovement(requestDTO.getType(), requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, company, fromStockLocation, stockProduct);

        productService.updateTotalQuantity(product.getId());

        stockProductRepository.save(stockProduct);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }

    @Transactional
    public MovementResponseDTO createTransferMovement(MovementRequestDTO requestDTO, Long currentCompanyId, Long currentUserId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(currentUserId, company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation fromStockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getFromStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        StockLocation toStockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getToStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndCompanyId(requestDTO.getProductId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        StockProduct fromStockProduct = stockProductRepository.findByStockLocationIdAndProductId(fromStockLocation.getId(), product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no local de estoque."));

        StockProduct toStockProduct = stockProductRepository.findByStockLocationIdAndProductId(toStockLocation.getId(), product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no local de estoque."));

        Movement entity = domainService.processTransferMovement(requestDTO.getType(), requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, company, fromStockLocation, toStockLocation, fromStockProduct, toStockProduct);

        productService.updateTotalQuantity(product.getId());

        stockProductRepository.save(fromStockProduct);
        stockProductRepository.save(toStockProduct);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }

    @Transactional
    public MovementResponseDTO createReturnMovement(MovementRequestDTO requestDTO, Long currentCompanyId, Long currentUserId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        User user = userRepository.findByIdAndCompanyId(currentUserId, company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        StockLocation toStockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getToStockLocationId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product product = productRepository.findByIdAndCompanyId(requestDTO.getProductId(), company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        StockProduct stockProduct = stockProductRepository.findByStockLocationIdAndProductId(toStockLocation.getId(), product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no local de estoque."));

        Movement entity = domainService.processReturnMovement(requestDTO.getType(), requestDTO.getQuantity(),
                requestDTO.getNote(), product, user, company, toStockLocation, stockProduct);

        productService.updateTotalQuantity(product.getId());

        stockProductRepository.save(stockProduct);
        repository.save(entity);
        return new MovementResponseDTO(entity);
    }
}
