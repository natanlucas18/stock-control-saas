package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.Company;
import com.hextech.estoque_api.domain.entities.Product;
import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.domain.entities.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.InvalidUnitMeasureException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StockLocationRepository stockLocationRepository;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllByCompanyId(Long currentCompanyId) {
        List<Product> products = repository.findAllByCompanyId(currentCompanyId);
        return products.stream().map(ProductResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findByIdAndCompanyId(Long id, Long currentCompanyId) {
        Product entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        return new ProductResponseDTO(entity);
    }

    public ProductResponseDTO insert(ProductRequestDTO requestDTO, Long currentCompanyId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
        StockLocation stockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getStockLocationId(), currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        Product entity = Product.createNewProduct(requestDTO.getName(), requestDTO.getPrice(), UnitMeasure.valueOf(requestDTO.getUnitMeasure()), company, stockLocation);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO requestDTO, Long currentCompanyId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
        Product entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        StockLocation stockLocation = stockLocationRepository.findByIdAndCompanyId(requestDTO.getStockLocationId(), currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        UnitMeasure unitMeasure;
        try {
            unitMeasure = UnitMeasure.valueOf(requestDTO.getUnitMeasure());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitMeasureException("Tipo de unidade de medida inválida.");
        }

        entity.updateProduct(requestDTO.getName(), requestDTO.getPrice(), unitMeasure, stockLocation);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    public void deleteByIdAndCompanyId(Long id, Long currentCompanyId) {
        Product entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DeletionConflictException("Falha na Integridade referencial.");
        }
    }
}


