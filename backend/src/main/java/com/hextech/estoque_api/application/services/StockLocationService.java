package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.StockLocationDTO;
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

    public StockLocationDTO insert(StockLocationDTO requestDTO) {
        StockLocation entity = new StockLocation();
        entity.setName(requestDTO.getName());

        Client client = new Client();
        client.setId(authContext.getCurrentClientId());
        entity.setClient(client);

        entity = repository.save(entity);

        return new StockLocationDTO(entity);
    }
}
