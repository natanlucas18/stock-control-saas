package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.ProductRequestDTO;
import com.hextech.estoque_api.application.dtos.ProductResponseDTO;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.Client;
import com.hextech.estoque_api.domain.entities.Product;
import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.domain.entities.UnitMeasure;
import com.hextech.estoque_api.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private StockLocationService stockLocationService;

    public ProductResponseDTO insert(ProductRequestDTO requestDTO) {
        Product entity = new Product();
        copyDtoToEntity(requestDTO, entity);
        entity.setQuantity(0);

        Client client = new Client();
        client.setId(authContext.getCurrentClientId());
        entity.setClient(client);

        entity = repository.save(entity);
        return new ProductResponseDTO(entity);
    }

    private void copyDtoToEntity(ProductRequestDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setUnitMeasure(UnitMeasure.valueOf(dto.getUnitMeasure()));
        entity.setStockLocation(stockLocationService.validateStockLocation(dto.getStockLocationId()));
    }
}


