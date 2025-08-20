package com.hextech.estoque_api.application.usecases;

import com.hextech.estoque_api.application.dtos.ProductDTO;
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
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(0);
        entity.setUnitMeasure(UnitMeasure.valueOf(dto.getUnitMeasure()));
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }
}
