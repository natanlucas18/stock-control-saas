package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.ProductRequestDTO;
import com.hextech.estoque_api.application.dtos.ProductResponseDTO;
import com.hextech.estoque_api.domain.entities.Product;
import com.hextech.estoque_api.domain.entities.UnitMeasure;
import com.hextech.estoque_api.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO requestDTO) {
        Product entity = new Product();
        entity.setName(requestDTO.getName());
        entity.setPrice(requestDTO.getPrice());
        entity.setQuantity(0);
        entity.setUnitMeasure(UnitMeasure.valueOf(requestDTO.getUnitMeasure()));
        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }
}
