package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.application.dtos.products.ProductResponseDTO;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.Company;
import com.hextech.estoque_api.domain.entities.Product;
import com.hextech.estoque_api.domain.entities.UnitMeasure;
import com.hextech.estoque_api.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private StockLocationService stockLocationService;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllByCompanyId() {
        List<Product> products = repository.findAllByCompanyId(authContext.getCurrentCompanyId());
        return products.stream().map(ProductResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findByIdAndCompanyId(Long id) {
        Product entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return new ProductResponseDTO(entity);
    }

    public ProductResponseDTO insert(ProductRequestDTO requestDTO) {
        Product entity = new Product();
        copyDtoToEntity(requestDTO, entity);
        entity.setQuantity(0);

        Company company = new Company();
        company.setId(authContext.getCurrentCompanyId());
        entity.setCompany(company);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO requestDTO) {
        Product entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        copyDtoToEntity(requestDTO, entity);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    public void deleteByIdAndCompanyId(Long id) {
        Product entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        repository.delete(entity);
    }

    private void copyDtoToEntity(ProductRequestDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setUnitMeasure(UnitMeasure.valueOf(dto.getUnitMeasure()));
        entity.setStockLocation(stockLocationService.validateStockLocation(dto.getStockLocationId()));
    }
}


