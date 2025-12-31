package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.unitMeasure.UnitMeasure;
import com.hextech.estoque_api.domain.exceptions.BusinessException;
import com.hextech.estoque_api.domain.exceptions.DeletionConflictException;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.infrastructure.repositories.UnitMeasureRepository;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnitMeasureService {

    @Autowired
    private UnitMeasureRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<UnitMeasureResponseDTO> findAllByCompanyId(String query, Long currentCompanyId, Pageable pageable) {
        Page<UnitMeasure> UnitMeasures = repository.findAllByNameAndCompanyId(query, currentCompanyId, pageable);
        return UnitMeasures.map(UnitMeasureResponseDTO::new);
    }

    @Transactional
    public UnitMeasureResponseDTO insert(UnitMeasureRequestDTO request, Long currentCompanyId) {
        Company company = companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        if (repository.existsByNameAndCompanyId(request.name(), company.getId()))
            throw new BusinessException("Nome de unidade de medida já existente.");

        if (repository.existsByAcronymAndCompanyId(request.acronym(), company.getId()))
            throw new BusinessException("Sigla de unidade de medida já existente.");

        UnitMeasure entity = UnitMeasure.createNewUnitMeasure(request.name(), request.acronym(), company);

        entity = repository.save(entity);
        return new UnitMeasureResponseDTO(entity);
    }

    @Transactional
    public UnitMeasureResponseDTO update(Long id, UnitMeasureRequestDTO request, Long currentCompanyId) {
        companyRepository.findById(currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
        UnitMeasure entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("U.M. não encontrada."));

        if (!entity.getAcronym().equals(request.acronym())) {
            if (productRepository.existsProductByUnitMeasureIdAndCompanyId(entity.getId(), currentCompanyId))
                throw new BusinessException("Não é permitido alterar a Sigla, U.M. possui produtos cadastrados.");
        }

        entity.updateUnitMeasure(request.name(), request.acronym());

        entity = repository.save(entity);
        return new UnitMeasureResponseDTO(entity);
    }

    @Transactional
    public void deleteByIdAndCompanyId(Long id, Long currentCompanyId) {
        UnitMeasure entity = repository.findByIdAndCompanyId(id, currentCompanyId)
                .orElseThrow(() -> new ResourceNotFoundException("U.M. não encontrada."));

        if (productRepository.existsProductByUnitMeasureIdAndCompanyId(entity.getId(), currentCompanyId))
            throw new DeletionConflictException("A U.M. possui produtos cadastrados, não pode ser deletada.");

        repository.delete(entity);
    }
}


