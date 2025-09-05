package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.security.AuthContext;
import com.hextech.estoque_api.domain.entities.Company;
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
    public List<StockLocationDTO> findAllByCompanyId() {
        List<StockLocation> result = repository.findByCompanyId(authContext.getCurrentCompanyId());
        return result.stream().map(StockLocationDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public StockLocationDTO findByIdAndCompanyId(Long id) {
        StockLocation entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        return new StockLocationDTO(entity);
    }

    public StockLocationDTO insert(StockLocationDTO requestDTO) {
        StockLocation entity = new StockLocation();
        entity.setName(requestDTO.name());

        Company company = new Company();
        company.setId(authContext.getCurrentCompanyId());
        entity.setCompany(company);

        entity = repository.save(entity);

        return new StockLocationDTO(entity);
    }

    public StockLocationDTO update(Long id, StockLocationDTO requestDTO) {
        StockLocation entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        entity.setName(requestDTO.name());
        entity = repository.save(entity);
        return new StockLocationDTO(entity);
    }

    public void deleteByIdAndCompanyId(Long id) {
        StockLocation entity = repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
        repository.delete(entity);
    }

    public StockLocation validateStockLocation(Long id) {
        return repository.findByIdAndCompanyId(id, authContext.getCurrentCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado"));
    }
}
