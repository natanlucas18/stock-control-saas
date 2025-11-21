package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.InvalidUnitMeasureException;
import com.hextech.estoque_api.domain.exceptions.ProductCodeAlreadyExistsException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockProductRepository;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResumeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StockLocationRepository stockLocationRepository;
    @Autowired
    private StockProductRepository stockProductRepository;

    @Transactional(readOnly = true)
    public Page<ProductResumeDTO> findAllByCompanyId(String name, Long currentCompanyId, Pageable pageable) {
        Page<Product> products = repository.findAllByNameAndCompanyId(name, currentCompanyId, pageable);
        return products.map(ProductResumeDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findByIdAndCompanyId(Long id, Long currentCompanyId) {
        Product entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        return new ProductResponseDTO(entity);
    }

    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO requestDTO, Long currentCompanyId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        validProductCode(requestDTO.getCode(), company.getId());

        UnitMeasure unitMeasure;
        try {
            unitMeasure = UnitMeasure.valueOf(requestDTO.getUnitMeasure());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitMeasureException("Tipo de unidade de medida inválida.");
        }

        Product entity = Product.createNewProduct(requestDTO.getCode(), requestDTO.getName(), requestDTO.getPrice(), requestDTO.getStockMax(),
                requestDTO.getStockMin(), unitMeasure, company);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO requestDTO, Long currentCompanyId) {
        companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
        Product entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

        if (!entity.isCodeEqual(requestDTO.getCode()))
            validProductCode(requestDTO.getCode(), currentCompanyId);

        UnitMeasure unitMeasure;
        try {
            unitMeasure = UnitMeasure.valueOf(requestDTO.getUnitMeasure());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitMeasureException("Tipo de unidade de medida inválida.");
        }

        entity.updateProduct(requestDTO.getCode(), requestDTO.getName(), requestDTO.getPrice(), requestDTO.getStockMax(),
                requestDTO.getStockMin(), unitMeasure);

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

    public void checkProductCode(String code, Long companyId) {
        validProductCode(code, companyId);
    }

    @Transactional
    public void updateTotalQuantity(Long productId) {
        BigDecimal quantity = stockProductRepository.sumByProductId(productId);
        repository.updateTotalQuantity(productId, quantity);
    }

    private void validProductCode(String code, Long companyId) {
        if (repository.existsByCodeAndCompanyId(code, companyId))
            throw new ProductCodeAlreadyExistsException("Código de produto já existente.");
    }
}


