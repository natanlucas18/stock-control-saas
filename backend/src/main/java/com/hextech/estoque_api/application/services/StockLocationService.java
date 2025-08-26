package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.Client;
import com.hextech.estoque_api.domain.entities.StockLocation;
import com.hextech.estoque_api.domain.repositories.StockLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockLocationService {

    @Autowired
    private AuthContext authContext;

    @Autowired
    private StockLocationRepository repository;

    @Transactional(readOnly = true)
    public List<StockLocationDTO> findAllByClientId() {
        List<StockLocation> result = repository.findByClientId(authContext.getCurrentClientId());
        return result.stream().map(StockLocationDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public StockLocationDTO findByIdAndClientId(Long id) {
        StockLocation entity = repository.findByIdAndClientId(id, authContext.getCurrentClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        return new StockLocationDTO(entity);
    }

    public StockLocationDTO insert(StockLocationDTO requestDTO) {
        StockLocation entity = new StockLocation();
        entity.setName(requestDTO.getName());

        Client client = new Client();
        client.setId(authContext.getCurrentClientId());
        entity.setClient(client);

        entity = repository.save(entity);

        return new StockLocationDTO(entity);
    }

    public StockLocationDTO update(Long id, StockLocationDTO requestDTO) {
        StockLocation entity = repository.findByIdAndClientId(id, authContext.getCurrentClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        entity.setName(requestDTO.getName());
        entity = repository.save(entity);
        return new StockLocationDTO(entity);
    }

    public void deleteByIdAndClientId(Long id) {
        StockLocation entity = repository.findByIdAndClientId(id, authContext.getCurrentClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        repository.delete(entity);
    }

    public StockLocation validateStockLocation(Long id) {
        return repository.findByIdAndClientId(id, authContext.getCurrentClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
    }
}
