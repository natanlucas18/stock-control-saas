package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.StockLocationRepository;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockLocationService {

    @Autowired
    private StockLocationRepository repository;
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public Page<StockLocationDTO> findAllByCompanyId(Long companyId, Pageable pageable) {
        Page<StockLocation> result = repository.findByCompanyId(companyId, pageable);
        return result.map(StockLocationDTO::new);
    }

    @Transactional(readOnly = true)
    public StockLocationDTO findByIdAndCompanyId(Long id, Long companyId) {
        StockLocation entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));
        return new StockLocationDTO(entity);
    }

    public StockLocationDTO insert(StockLocationDTO requestDTO, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        StockLocation entity = StockLocation.createNewStockLocation(requestDTO.name(), company);

        entity = repository.save(entity);
        return new StockLocationDTO(entity);
    }

    public StockLocationDTO update(Long id, StockLocationDTO requestDTO, Long companyId) {
        StockLocation entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));

        entity.updateStockLocation(requestDTO.name());

        entity = repository.save(entity);
        return new StockLocationDTO(entity);
    }

    public void deleteByIdAndCompanyId(Long id, Long companyId) {
        StockLocation entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Local de estoque não encontrado."));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DeletionConflictException("Falha na Integridade referencial.");
        }
    }
}
